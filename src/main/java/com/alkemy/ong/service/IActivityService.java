package com.alkemy.ong.service;


import com.alkemy.ong.dto.ActivityBasicDTO;
import com.alkemy.ong.dto.ActivityDTO;

import java.util.List;

public interface IActivityService {
    ActivityDTO save (ActivityDTO dto);

    List<ActivityDTO> getAll();

    ActivityDTO update (Long id, ActivityBasicDTO dto);
}
