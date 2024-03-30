package com.ego.doan_ego.controller;

import com.ego.doan_ego.response.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IBaseController<T, E extends T, TypeId> {
    ResponseEntity<?> add(@RequestBody E entity);

    ResponseEntity<?> edit(@RequestBody E entity, @PathVariable(name = "id") TypeId typeId);

    ResponseEntity<?> delete(@PathVariable(name = "id") TypeId typeId);

    ResponseEntity<?> getAll(Pageable pageable);

    ResponseEntity<?> getById(@PathVariable(name = "id") TypeId typeId);

}
