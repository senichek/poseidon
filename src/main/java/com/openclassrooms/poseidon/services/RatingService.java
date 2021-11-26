package com.openclassrooms.poseidon.services;

import java.util.List;
import com.openclassrooms.poseidon.domain.Rating;

public interface RatingService {
    Rating create(Rating rt) throws Exception;
    Rating update(Rating rt) throws Exception;
    Rating delete(Integer id) throws Exception;
    List<Rating> getAll();
    Rating getById(Integer id) throws Exception;
}
