package com.example.mapper;

import com.example.dto.CarDTO;
import com.example.entity.BrandEntity;
import com.example.entity.CarEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-09T04:13:10+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class CarMapperImpl implements CarMapper {

    @Override
    public CarDTO fromCarEntityToCarDTO(CarEntity carEntity) {
        if ( carEntity == null ) {
            return null;
        }

        Integer year = null;
        String brand = null;
        Long id = null;
        String model = null;

        year = carEntity.getYearOfManufacture();
        brand = carEntityBrandName( carEntity );
        id = carEntity.getId();
        model = carEntity.getModel();

        CarDTO carDTO = new CarDTO( id, brand, model, year );

        return carDTO;
    }

    @Override
    public CarEntity fromCarDTOToCarEntity(CarDTO carDTO) {
        if ( carDTO == null ) {
            return null;
        }

        CarEntity carEntity = new CarEntity();

        carEntity.setYearOfManufacture( carDTO.year() );
        carEntity.setId( carDTO.id() );
        carEntity.setModel( carDTO.model() );

        return carEntity;
    }

    private String carEntityBrandName(CarEntity carEntity) {
        if ( carEntity == null ) {
            return null;
        }
        BrandEntity brand = carEntity.getBrand();
        if ( brand == null ) {
            return null;
        }
        String name = brand.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
