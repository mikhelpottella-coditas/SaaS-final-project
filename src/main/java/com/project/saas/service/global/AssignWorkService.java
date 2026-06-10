package com.project.saas.service.global;

import com.project.saas.dto.global.responceDto.AssignWorkResponseDto;
import com.project.saas.entity.master.AssignWork;
import com.project.saas.enums.ComplaintStatus;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.AssignWorkRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AssignWorkService {

    private final AssignWorkRepo assignWorkRepo;


    public void save(AssignWork assignWork) {
        assignWorkRepo.save(assignWork);
    }

    public List<AssignWorkResponseDto> getByEmpId(Long empId, int page, int size, String sortBy, boolean ascending, String search, ComplaintStatus filter) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);


        List<AssignWork> assignWorkList =  assignWorkRepo.findAllByAssignedElectrician_IdAndComplaintStatus(empId,filter,pageable).getContent();

        List<AssignWorkResponseDto> assignWorkResponseDtoList = new ArrayList<>();

        assignWorkList.forEach(assignWork -> assignWorkResponseDtoList.add(new AssignWorkResponseDto(assignWork.getId(), assignWork.getAssignedElectrician().getId(), assignWork.getCustomer().getId(), assignWork.getWorkType(), assignWork.getWorkDescription(), assignWork.getComplaintStatus(), assignWork.getAssignedAt())));


        log.info("fetching the work of electrician with id : {}",empId);
        if(search.isEmpty()){ return assignWorkResponseDtoList;}

        log.info("getting the list of matches the search while sending tasks to electrician");
        return assignWorkResponseDtoList.stream().filter(a->a.workDescription().contains(search)).toList();


    }


    public AssignWorkResponseDto getWorkById(Long taskId) {

        AssignWork assignWork = assignWorkRepo.findById(taskId).orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND,"task id not found with the given id"));

        log.info("getting the work of electrician with task id : {}",taskId);
        return new AssignWorkResponseDto(assignWork.getId(), assignWork.getAssignedElectrician().getId(), assignWork.getCustomer().getId(), assignWork.getWorkType(),assignWork.getWorkDescription() ,assignWork.getComplaintStatus() , assignWork.getAssignedAt());

    }



    public String markAsDone(Long taskId) {
        AssignWork assignWork = assignWorkRepo.findById(taskId).orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND,"task id not found"));

        if(assignWork.getComplaintStatus() != ComplaintStatus.IN_PROGRESS) throw new CustomException(HttpStatus.CONFLICT,"complaintStatus is not in progress");

        assignWork.setComplaintStatus(ComplaintStatus.RESOLVED);
        assignWorkRepo.save(assignWork);

        log.info("the task is putting as resolved by electrician");
        return "the task is done";
    }

    public String raiseTask(Long taskId) {

        AssignWork assignWork = assignWorkRepo.findById(taskId).orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND,"task id not found"));

        if(assignWork.getComplaintStatus() != ComplaintStatus.IN_PROGRESS) throw new CustomException(HttpStatus.CONFLICT,"complaintStatus is not in progress");
        assignWork.setComplaintStatus(ComplaintStatus.RAISED_TO_CITY_MANAGER);
        assignWorkRepo.save(assignWork);

        log.info("the task is putting as raised by electrician");
        return "the task is raised to city manager";
    }
}
