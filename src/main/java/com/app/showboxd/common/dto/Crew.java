package com.app.showboxd.common.dto;
import lombok.Data;

@Data
public class Crew{
    public String department;
    public String job;
    public String credit_id;
    public boolean adult;
    public int gender;
    public int id;
    public String known_for_department;
    public String name;
    public String original_name;
    public double popularity;
    public String profile_path;
}