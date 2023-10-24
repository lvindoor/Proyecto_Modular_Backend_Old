package com.udg.mirutina.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(hidden = true) // not visible on swagger
  private Long id;

  @Column(length = 50)
  private String name;

  @Column(length = 255)
  private String description;

  @Column(name = "is_active")
  private Boolean isActive = true;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  @ApiModelProperty(hidden = true) // not visible on swagger
  private Date createdAt;  

}
