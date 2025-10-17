package ru.srfholding.trackermodels.base;

public interface BaseDbAttribute<T> {

    T getCode(BaseDbAttribute<T> attribute);

    BaseDbAttribute<Integer> getByCode(T code);
}
