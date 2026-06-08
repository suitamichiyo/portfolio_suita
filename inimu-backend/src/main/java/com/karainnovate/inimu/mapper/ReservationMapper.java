package com.karainnovate.inimu.mapper;

import com.karainnovate.inimu.model.Reservation;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ReservationMapper {

    @Insert("INSERT INTO reservations (slot_id, name, name_kana, email, phone, num_people, allergy_note) " +
            "VALUES (#{slotId}, #{name}, #{nameKana}, #{email}, #{phone}, #{numPeople}, #{allergyNote})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Reservation reservation);
}
