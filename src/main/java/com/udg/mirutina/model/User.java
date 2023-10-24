package com.udg.mirutina.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(hidden = true) // not visible on swagger
  private Long id;
  
  @Column(unique = true, length = 50)
  private String username;

  @Column
  private String password;

  @Column(unique = true, length = 50)
  private String email;

  @Column
  @ApiModelProperty(example = "false") // not visible on swagger
  private Boolean google = false;

  @Column(name = "is_active")
  @ApiModelProperty(example = "true") // not visible on swagger
  private Boolean isActive = true;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  @ApiModelProperty(hidden = true) // not visible on swagger
  private Date createdAt;  

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  // @JsonIgnore // this attribute is ignore in the JSON response
  // @ApiModelProperty(hidden = true) // not visible on swagger
  private List<Role> roles;

  public User() {
    this.roles = new ArrayList<>();
  }

}
