package com.ego.doan_ego.service.interfaceService;

import com.ego.doan_ego.response.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IBaseService<T, E extends T, TypeID> {
    ResponseEntity<?> store(E entity) throws Exception;

    ResponseEntity<?> update(E entity, TypeID id) throws Exception;

    ResponseEntity<?> destroy(TypeID id) throws Exception;

    ResponseEntity<?> getAll(Pageable pageable) throws Exception;

    ResponseEntity<?> getById(TypeID id) throws Exception;

}
