package com.openclassrooms.poseidon.services;

import java.util.List;

import javax.transaction.Transactional;
import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Override
    public Rating create(Rating rt) throws Exception {
        if (rt.getOrderNumber() < 0 || rt.getOrderNumber() == null) {
            throw new Exception("Order Number cannot be negative or empty.");
        } else if (rt.getMoodysRating().isEmpty() || rt.getSandPRating().isEmpty() || rt.getFitchRating().isEmpty()) {
            throw new Exception("One of the fields [Moody's Rating, Sand&P Rating or Fitch Rating] is empty or null.");
        } else {
            Rating created = ratingRepository.save(rt);
            log.info("Created {}", created);
            return created;
        }
    }

    @Override
    @Transactional
    public Rating update(Rating rt) throws Exception {
        Rating toUpdate = ratingRepository.findByRatingId(rt.getId());
        if (toUpdate == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", rt.getId()));
        } else if (rt.getOrderNumber() < 0 || rt.getOrderNumber() == null) {
            throw new Exception("Order Number cannot be negative or empty.");
        } else if (rt.getMoodysRating().isEmpty() || rt.getSandPRating().isEmpty() || rt.getFitchRating().isEmpty()) {
            throw new Exception("One of the fields [Moody's Rating, Sand&P Rating or Fitch Rating] is empty or null.");
        } else {
            if (!rt.getMoodysRating().equals(toUpdate.getMoodysRating())) {
                toUpdate.setMoodysRating(rt.getMoodysRating());
            }
            if (!rt.getSandPRating().equals(toUpdate.getSandPRating())) {
                toUpdate.setSandPRating(rt.getSandPRating());
            }
            if (!rt.getFitchRating().equals(toUpdate.getFitchRating())) {
                toUpdate.setFitchRating(rt.getFitchRating());
            }
            if (rt.getOrderNumber() != toUpdate.getOrderNumber()) {
                toUpdate.setOrderNumber(rt.getOrderNumber());
            }
            ratingRepository.save(toUpdate);
            log.info("Updated {}", toUpdate);
            return toUpdate;
        }
    }

    @Override
    public Rating delete(Integer id) throws Exception {
        Rating toDelete = ratingRepository.findByRatingId(id);
        if (toDelete == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            ratingRepository.delete(toDelete);
            log.info("Deleted {}.", toDelete);
            return toDelete;
        }
    }

    @Override
    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating getById(Integer id) throws Exception {
        Rating rating = ratingRepository.findByRatingId(id);
        if (rating == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            return rating;
        }
    }
}
