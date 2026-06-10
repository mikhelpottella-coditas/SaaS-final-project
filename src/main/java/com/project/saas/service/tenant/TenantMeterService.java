package com.project.saas.service.tenant;

import com.project.saas.dto.tenant.request.MeterRequestDto;
import com.project.saas.dto.tenant.response.MeterResponseDto;
import com.project.saas.entity.tenant.TenantCustomerMeter;
import com.project.saas.entity.tenant.TenantMeter;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.TenantCustomerMeterRepo;
import com.project.saas.repo.tenant.TenantMeterRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantMeterService {

    private final TenantMeterRepo meterRepo;
    private final TenantCustomerMeterService customerMeterService;

    public List<MeterResponseDto> getAll() {
        List<TenantMeter> tenantMeterList = meterRepo.findAll();
        log.info("tenantMeterList that belong to a state");
        return tenantMeterList.stream().map(t->new MeterResponseDto(t.getId(), t.getType(), t.getRatePerUnit(), t.getPhotosRequired(), t.getIntervalBtwPhotos())).toList();
    }

    public TenantMeter getById(Long id){
        return meterRepo.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, " the meter not found with the given id"));
    }

    public MeterResponseDto saveMeter(@Valid MeterRequestDto meterRequestDto) {
        log.info("Starting saving meter for tenant {}", meterRequestDto.type());
        TenantMeter meter = TenantMeter.builder()
                .type(meterRequestDto.type())
                .intervalBtwPhotos(meterRequestDto.intervalBtwPhotos())
                .photosRequired(meterRequestDto.photosRequired())
                .ratePerUnit(meterRequestDto.ratePerUnit())
                .build();

        meterRepo.save(meter);
        log.info("saving meter for tenant {} is successful", meterRequestDto.type());
        return new MeterResponseDto(meter.getId(), meter.getType(),meter.getRatePerUnit(), meter.getPhotosRequired(), meter.getIntervalBtwPhotos());
    }

    public MeterResponseDto updateMeter(Long id, @Valid MeterRequestDto meterRequestDto) {
        log.info("starting update meter for tenant {}", meterRequestDto.type());
        TenantMeter meter = getById(id);

        meter.setRatePerUnit(meterRequestDto.ratePerUnit());
        meter.setIntervalBtwPhotos(meterRequestDto.photosRequired());
        meter.setPhotosRequired(meterRequestDto.photosRequired());
        meter.setType(meterRequestDto.type());

        meterRepo.save(meter);
        log.info("updating the meter details successful with the id : {}",id);
        return new MeterResponseDto(meter.getId(), meter.getType(),meter.getRatePerUnit(), meter.getPhotosRequired(), meter.getIntervalBtwPhotos());
    }

    public String deleteMeter(Long id) {
        log.info("starting delete meter for tenant {}", id);
        TenantMeter meter = getById(id);
        List<TenantCustomerMeter> customerMeterList = meter.getTenantCustomerMeterList();

        customerMeterList.forEach(cmt -> {
            cmt.setTenantMeter(null);
            customerMeterService.save(cmt);
        });

        meterRepo.delete(meter);
        log.info("deleting the meter details successful with the id : {}",id);
        return "deleting the meter details successful with the id : "+id;
    }

    public List<MeterResponseDto> getAllMeters(int page, int size, String sortBy, boolean ascending, String search) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);


        log.info("Starting all meters for tenant");
        List<TenantMeter> meterList =  meterRepo.findAll(pageable).getContent();
        log.info("getting all meter details successful");
        List<MeterResponseDto> meterResponseDtoList =  meterList.stream().map(meter-> new MeterResponseDto(meter.getId(), meter.getType(), meter.getRatePerUnit(), meter.getPhotosRequired(), meter.getIntervalBtwPhotos())).toList();

        if(search.isEmpty()) return meterResponseDtoList;

        return meterResponseDtoList.stream().filter(m->m.type().contains(search)).toList();
    }

    public MeterResponseDto getMeterById(Long id) {
        log.info("stating to get meter details by id : {}", id);
        TenantMeter meter = getById(id);
        log.info("getting meter details successful by id : {}",id);
        return new MeterResponseDto(meter.getId(), meter.getType(),meter.getRatePerUnit(), meter.getPhotosRequired(), meter.getIntervalBtwPhotos());
    }



    public List<MeterResponseDto> getByCustomerId(Long customerId) {

        List<TenantCustomerMeter> tenantCustomerMeterList = customerMeterService.getAllCustomerMetersByCustomerId(customerId);

        List<TenantMeter> tenantMeterList = tenantCustomerMeterList.stream().map(TenantCustomerMeter::getTenantMeter).toList();

        log.info("fetching all the meter details of the customer");
        return tenantMeterList.stream().map(m-> new MeterResponseDto(m.getId(), m.getType(), m.getRatePerUnit(), m.getPhotosRequired(), m.getIntervalBtwPhotos())).toList();
    }
}
