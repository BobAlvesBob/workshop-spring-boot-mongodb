package com.alvesbob.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alvesbob.workshopmongo.domain.User;
import com.alvesbob.workshopmongo.dto.UserDTO;
import com.alvesbob.workshopmongo.repository.UserRepository;
import com.alvesbob.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> findAll(){
		return repo.findAll();
				
	}
	
	public User findById(String id) {
        Optional<User> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
        
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
		
	}
	
	public User update(User obj) {
	    Optional<User> optionalNewObj = repo.findById(obj.getId());
	    
	    if (!optionalNewObj.isPresent()) {
	        throw new ObjectNotFoundException("Objeto não encontrado");
	    }
	    
	    User newObj = optionalNewObj.get();
	    updateData(newObj, obj);
	    return repo.save(newObj);
	}
	
	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
		
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}

}
