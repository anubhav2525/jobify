package com.naukari.server.model.entity.jobs;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*; // this already includes the correct @Id

@Entity
@Table(name = "job_app_ques_ans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobAppQuesAns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
