package com.ley.innovation.contest.business.vo;

import com.ley.innovation.contest.business.entity.Carousel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ley
 **/
@Data
@NoArgsConstructor
public class CarouselVO extends Carousel {

    private String eventTitle;
}
