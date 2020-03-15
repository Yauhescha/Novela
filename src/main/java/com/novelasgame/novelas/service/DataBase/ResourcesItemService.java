package com.novelasgame.novelas.service.DataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.ResourceItem;
import com.novelasgame.novelas.repository.ResourcesItemRepository;

@Service
public class ResourcesItemService implements CrudService<ResourceItem> {

    @Autowired
    ResourcesItemRepository repository;

    @Override
    public boolean create(ResourceItem entity) {
        try {
            repository.saveAndFlush(entity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public ResourceItem read(long id) {
        return repository.findById(id).isPresent() ? repository.findById(id).get() : null;
    }

    @Override
    public boolean update(ResourceItem entity) {
        try {
            repository.saveAndFlush(entity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    
    public ResourceItem findByType(String type) {
        return repository.findByType(type);
    }

    public ResourceItem findByGame(Game game) {
        return repository.findByGame(game);
    }
}
