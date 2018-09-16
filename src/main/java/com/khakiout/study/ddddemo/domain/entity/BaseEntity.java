package com.khakiout.study.ddddemo.domain.entity;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toComplex;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.registry.impl.SimpleRegistry;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import java.util.Date;

/**
 * Abstract class for entity.
 */
public abstract class BaseEntity<T> {

  protected T id;
  private Date createdAt;
  private Date updatedAt;

  BaseEntity() {
    this.createdAt = this.updatedAt = new Date();
  }

  BaseEntity(T id, Date createdAt, Date updatedAt) {
    this.id = id;
    this.createdAt = (createdAt != null) ? new Date(createdAt.getTime()) : new Date();
    this.updatedAt = (updatedAt != null) ? new Date(updatedAt.getTime()) : this.createdAt;
  }

  public T getId() {
    return id;
  }

  public Date getCreatedAt() {
    return new Date(createdAt.getTime());
  }

  public Date getUpdatedAt() {
    return new Date(updatedAt.getTime());
  }

  /**
   * Validate the entity based on the rules attached to it.
   */
  public void validate() throws EntityValidationException {
    ComplexResult result = FluentValidator.checkAll()
        .failOver()
        .configure(new SimpleRegistry())
        .on(this)
        .doValidate()
        .result(toComplex());

    if (!result.isSuccess()) {
      throw new EntityValidationException(result);
    }
  }
}
