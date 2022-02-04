package com.example.ToDO;

import com.example.ToDO.model.Mission;
import com.example.ToDO.model.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.rmi.MarshalledObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Controller
public class ToDoController {

    @Autowired
    MissionRepository missionRepository;

    @PostMapping("/")
    public String add (@RequestParam String name,
                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date,
                       @RequestParam String priority,
                       Map<String,Object> model)
    {
        Mission mission = new Mission(name, date, priority);
        missionRepository.save(mission);
        Iterable<Mission> missions = missionRepository.findAll();
        model.put("missions",missions);
        return "index";
    }

    @GetMapping("/")
    public String getAll(Map<String, Object> model)
    {
        Iterable<Mission> missions = missionRepository.findAll();
        model.put("missions",missions);
        return "index";
    }

    @GetMapping("/{id}" )
    public String get(@PathVariable int id, Map<String,Object> model){
        Mission seekingMission = missionRepository.findById(id);
        if (!missionRepository.existsById(id))
            return "error404";
        model.put("mission", seekingMission);
        return "mission";
    }

    @DeleteMapping("/")
    public String delAll()
    {
        missionRepository.deleteAll();
        return "index";
    }

    @DeleteMapping ("/{id}")
    public String del(@PathVariable int id)
    {
        missionRepository.deleteById(id);
        return "error404";
    }

    @PutMapping("/{id}")
    public String put(@PathVariable int id,
                      @RequestParam String name,
                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date,
                      @RequestParam String priority,
                      Map<String,Object> model){
        Mission seekingMission = missionRepository.findById(id);
        if (!name.isEmpty()) seekingMission.setName(name);
        if (!date.toString().equals("0001-01-01")) seekingMission.setDate(date);
        if (!priority.isEmpty()) seekingMission.setPriority(priority);
        missionRepository.save(seekingMission);
        model.put("mission", missionRepository.findById(id));
        return "mission";
    }
}
