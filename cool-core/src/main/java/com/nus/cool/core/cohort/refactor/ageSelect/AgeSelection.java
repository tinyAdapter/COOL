package com.nus.cool.core.cohort.refactor.ageSelect;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nus.cool.core.cohort.refactor.utils.DateUtils;
import com.nus.cool.core.cohort.refactor.utils.TimeUtils;
import com.nus.cool.core.cohort.refactor.utils.TimeWindow;

/**
 * Store Age condition and filter valid age for cohort Analysis.
 */
public class AgeSelection {

    private TimeUtils.TimeUnit unit;

    private Integer min, max;

    // For current implementation, we ignore different interval for age
    @JsonIgnore
    private final int interval = 1;

    @JsonIgnore
    public final static int DefaultNullAge = -1;
    /**
     * After read from json directly
     * if min and max is null, we should initialize it with the extremum
     */
    public void init() {
        if (this.min == null)
            this.min = Integer.MIN_VALUE;
        if (this.max == null)
            this.max = Integer.MAX_VALUE;
    }

    /**
     * According to the ageSelection, return the age of this action tuple
     * 
     * @param birthDate
     * @param actionTime
     * @return if the age is out of range return -1 else return age
     *         TODO(lingze): Long to int will raise loss of precision,
     *         future implementation should focus on long type data.
     */
    public int generateAge(Calendar birthDate, Calendar actionTime) {
        TimeWindow tw = DateUtils.getDifference(birthDate, actionTime, this.unit);
        int age = (int) tw.getLength();
        age = age > max || age < min ? -1 : age;
        return age;
    }
}
