package com.karainnovate.inimu.mapper;

import com.karainnovate.inimu.model.Contact;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {

    @Select("SELECT r.*, s.slot_date, s.start_time, s.end_time " +
            "FROM reservations r JOIN workshop_slots s ON r.slot_id = s.id " +
            "ORDER BY r.created_at DESC")
    List<Map<String, Object>> findAllReservations();

    @Select("SELECT * FROM contacts ORDER BY created_at DESC")
    List<Contact> findAllContacts();

    @Update("UPDATE reservations SET status = #{status} WHERE id = #{id}")
    void updateReservationStatus(@Param("id") Long id, @Param("status") String status);

    @Update("UPDATE contacts SET status = #{status} WHERE id = #{id}")
    void updateContactStatus(@Param("id") Long id, @Param("status") String status);
}
