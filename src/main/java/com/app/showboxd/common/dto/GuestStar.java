package com.app.showboxd.common.dto;
import lombok.Data;

@Data
public class GuestStar{
    public String character;
    public String credit_id;
    public int order;
    public boolean adult;
    public int gender;
    public int id;
    public String known_for_department;
    public String name;
    public String original_name;
    public double popularity;
    public String profile_path;
}