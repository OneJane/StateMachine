package com.onejane.machine.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer groupId;

    private String groupName;

    private Integer status;

    private Boolean isAdvance;


}
