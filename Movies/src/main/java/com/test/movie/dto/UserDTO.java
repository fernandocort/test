package com.test.movie.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDTO implements Serializable{

   /**
    * 
    */
   private static final long serialVersionUID = -6082111591876359086L;

   
   private String username;
   private String password;
}
