package cz.muni.fi.pa165.yellowlibrary.service;

import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @author Norbert Slivka
 */
@Service
public interface DateService {
  /**
   * Returns calendar with current date set.
   */
  Calendar now();
}
