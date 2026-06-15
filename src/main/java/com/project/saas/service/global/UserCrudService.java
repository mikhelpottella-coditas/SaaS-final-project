package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.BillerResponseDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.master.RefreshToken;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.RefreshTokenRepository;
import com.project.saas.repo.global.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCrudService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ManagerUserService managerUserService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;


    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, " the user user trying to assess is not found"));
    }

    public String updateProfile(UserRequestDto userRequestDto) {

        User user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user == null) throw new CustomException(HttpStatus.NOT_FOUND, "the user is not found to update");

        if (userRequestDto.firstName() != null && !userRequestDto.firstName().isEmpty())
            user.setFirstName(userRequestDto.firstName());
        if (userRequestDto.lastName() != null && !userRequestDto.lastName().isEmpty())
            user.setLastName(userRequestDto.lastName());
        if (userRequestDto.phone() != null && userRequestDto.phone().length() == 10 && userRequestDto.phone().matches("^\\d+$"))
            user.setPhone(userRequestDto.phone());

        userRepository.save(user);

        log.info("update user profile successfully with the id : {}", user.getId());
        return "admin data updated successfully";

    }


    public UserResponseDto getProfile() {
        User user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user == null) throw new CustomException(HttpStatus.NOT_FOUND, "the user is not found to update");
        return new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getCreatedAt(), user.getUpdatedAt());
    }


    public void delete(User cityHead) throws Exception {
        userRepository.delete(cityHead);
    }


    public String deleteById(Long id) {
        User user = getById(id);

        List<RefreshToken> refreshTokenList = refreshTokenRepository.findAllByUser(user).orElse(null);

        if(refreshTokenList!=null){
            refreshTokenRepository.deleteAll(refreshTokenList);
        }

        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "the user is not possible to delete since they are dependent things in this application");
        }
        return "deleted successfully ";
    }

    public List<UserResponseDto> getAllOperationHead(int page, int size, String sortBy, boolean ascending, String search) {

        log.info("getting all the user who belong to the particular role operation heads");
        List<User> userList = managerUserService.getAllUsers(page, size, sortBy, ascending, Role.OPERATIONAL_HEAD);

        List<UserResponseDto> userResponceDtoList = new ArrayList<>();
        userList.forEach(u-> userResponceDtoList.add( new UserResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt())));
        log.info("return the data of the operation heads");
        if (search.isEmpty())return userResponceDtoList;
        return userResponceDtoList.stream().filter(s->s.firstName().contains(search)).toList();
    }

    public UserResponseDto getOperationHeadById(Long id) {

        User user = userRepository.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "the sales point not for found with the given id : "+id));
        if(!user.getRole().equals(Role.OPERATIONAL_HEAD)) throw new CustomException(HttpStatus.NOT_FOUND, "the sales point not for found with the given id : "+id);
        log.info("returning the operation head");
        return new UserResponseDto(user.getId(), user.getFirstName(),user.getLastName(),user.getEmail(), user.getPhone(), user.getCreatedAt(),user.getUpdatedAt());

    }

    public List<BillerResponseDto> getAllWorkers(int page, int size, String sortBy, boolean ascending, String search, Role role) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if(!(role ==Role.BILLER || role == Role.ELECTRICIAN || role == Role.CMR)){
            throw new CustomException(HttpStatus.BAD_REQUEST, " you are not allowed to access these role");
        }

        List<User> billerList = userService.getByRole(role, pageable);

        List<BillerResponseDto> billerResponseDtoList = new ArrayList<>();

        billerList.forEach(u->
        {
            Long areaId = u.getBillerAreas()==null?null:u.getBillerAreas().getId();
            billerResponseDtoList.add(new BillerResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt(), areaId, areaId != null));
        });

        if(search.isEmpty()) return billerResponseDtoList;

        log.info("fetching all the biller details ");
        return billerResponseDtoList.stream().filter(b-> b.firstName().contains(search)).toList();


    }

    public String logout() {
        User user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user == null) throw new CustomException(HttpStatus.NOT_FOUND, "the user is not found to update");

        List<RefreshToken> refreshTokenList = refreshTokenService.getToken(user);

        return refreshTokenService.delete(refreshTokenList);
    }
}
