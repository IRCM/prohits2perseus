/*
 * Copyright (c) 2020 Institut de recherches cliniques de Montreal (IRCM)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package ca.qc.ircm.javafx;

import javafx.util.StringConverter;

/**
 * {@link StringConverter} that returns null when an exception is thrown by the supplied converter.
 */
public class NullOnExceptionStringConverter<T> extends StringConverter<T> {
  /**
   * Converter to delegate method calls.
   */
  private final StringConverter<T> converter;

  /**
   * Creates {@link NullOnExceptionStringConverter}.
   * 
   * @param converter
   *          converter
   */
  public NullOnExceptionStringConverter(StringConverter<T> converter) {
    this.converter = converter;
  }

  /**
   * Converts the string provided into an object defined by the supplied converter.
   *
   * <p>
   * If an exception is thrown by the converter, <code>null</code> is returned.
   * </p>
   * 
   * @param string
   *          the {@code String} to convert
   * @return an object representation of the string passed in, or <code>null</code> if the converter
   *         threw an exception
   */
  @Override
  public T fromString(String string) {
    try {
      return converter.fromString(string);
    } catch (Throwable e) {
      return null;
    }
  }

  /**
   * Converts the object provided into its string form. Format of the returned string is defined by
   * the specific converter.
   *
   * <p>
   * If an exception is thrown by the converter, <code>null</code> is returned.
   * </p>
   * 
   * @param object
   *          the object of type {@code T} to convert
   * @return a string representation of the object passed in, or <code>null</code> if the converter
   *         threw an exception
   */
  @Override
  public String toString(T object) {
    try {
      return converter.toString(object);
    } catch (Throwable e) {
      return null;
    }
  }
}
