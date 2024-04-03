package com.ego.doan_ego.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Data
public class Exam {

    @Id
    private Long id;

    private String name;

    private LocalDateTime timeStart;

    private LocalDateTime timeEnd;

    @OneToMany
    private List<Test> tests = new ArrayList<>();

    public long getTimeDuration() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/HoChiMinh"));
        ZonedDateTime tStartConverter = ZonedDateTime.of(timeStart, ZoneId.systemDefault());
        ZonedDateTime tEndConverter = ZonedDateTime.of(timeEnd, ZoneId.systemDefault());
        return tEndConverter.toInstant().toEpochMilli() - tStartConverter.toInstant().toEpochMilli();
    }
}
