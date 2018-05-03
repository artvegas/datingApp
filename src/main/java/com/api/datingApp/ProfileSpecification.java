package com.api.datingApp;



import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.api.datingApp.model.Profile;
import com.api.datingApp.model.SearchCriteria;

public class ProfileSpecification implements Specification<Profile> {
	 
    private SearchCriteria criteria;
 
    public ProfileSpecification(SearchCriteria searchCriteria) {
		this.criteria = searchCriteria;
	}

	@Override
    public Predicate toPredicate
      (Root<Profile> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
  
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
              root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } 
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
              root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } 
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                  root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }else if(criteria.getOperation().equalsIgnoreCase("=")) {
        		if(root.get(criteria.getKey()).getJavaType() == String.class) {
        			return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        		}
        }else if(criteria.getOperation().equalsIgnoreCase("!=")) {
        		return builder.notEqual(
        				root.<String>get(criteria.getKey()), criteria.getValue());
        }else if(criteria.getOperation().equalsIgnoreCase("city")) {
        		return builder.like(root.get(criteria.getKey()).get("person").get("city"), "%" + criteria.getValue() + "%");
        }else if(criteria.getOperation().equalsIgnoreCase("state")) {
    			return builder.like(root.get(criteria.getKey()).get("person").get("state"), "%" + criteria.getValue() + "%");
        }else if(criteria.getOperation().equalsIgnoreCase("street")) {
    			return builder.like(root.<String>get(criteria.getKey()).get("person").get("street"), "%"+criteria.getValue() + "%");
        }else if(criteria.getOperation().equalsIgnoreCase("zipcode")) {
    			return builder.equal(root.get(criteria.getKey()).get("zipcode").get("zipcode"), criteria.getValue());
        }else if(criteria.getOperation().equalsIgnoreCase("ssn")) {
    			return builder.notEqual(root.get(criteria.getKey()).get("ssn"), criteria.getValue());
        }
        return null;
    }


}
