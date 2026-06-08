package com.karainnovate.inimu.mapper;

import com.karainnovate.inimu.model.WorkshopSlot;
import org.apache.ibatis.annotations.*;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface WorkshopSlotMapper {

    @Select("SELECT * FROM workshop_slots WHERE slot_date = #{date} AND is_active = TRUE")
    List<WorkshopSlot> findByDate(LocalDate date);

    @Select("SELECT * FROM workshop_slots WHERE slot_date BETWEEN #{from} AND #{to} AND is_active = TRUE")
    List<WorkshopSlot> findByDateRange(LocalDate from, LocalDate to);

    @Select("SELECT * FROM workshop_slots WHERE id = #{id}")
    WorkshopSlot findById(Long id);

    @Update("UPDATE workshop_slots SET reserved_count = reserved_count + #{numPeople} WHERE id = #{id}")
    void incrementReservedCount(@Param("id") Long id, @Param("numPeople") int numPeople);
}
