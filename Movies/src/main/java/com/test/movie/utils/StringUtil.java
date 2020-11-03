package com.test.movie.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtil {

   /**
    * Return True if the string is null or empty (after possible trimming);
    * otherwise false.
    */
   public static boolean notNullOrEmpty(final String str, final boolean trim) {
      if (trim) {
         return !(str == null || str.trim().equals(""));
      } else {
         return !(str == null || str.equals(""));
      }
   }
}
