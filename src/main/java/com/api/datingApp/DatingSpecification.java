package com.api.datingApp;



import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.api.datingApp.model.Dates;
import com.api.datingApp.model.SearchCriteria;

public class DatingSpecification implements Specification<Dates> {
	 
    private SearchCriteria criteria;
 
    public DatingSpecification(SearchCriteria searchCriteria) {
		this.criteria = searchCriteria;
	}

	@Override
    public Predicate toPredicate
      (Root<Dates> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
  
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
        		}else {
        			return builder.equal(
        					root.get(criteria.getKey()), criteria.getValue());
        		}
        }else if(criteria.getOperation().equalsIgnoreCase("!=")) {
        		return builder.notEqual(
        				root.<String>get(criteria.getKey()), criteria.getValue());
        }else if(criteria.getOperation().equalsIgnoreCase("profile1")) {
        		return builder.equal(root.get(criteria.getKey())
        				.get("profile1").get("profileId"), criteria.getValue());
        }else if(criteria.getOperation().equalsIgnoreCase("date>")) {
    		return builder.greaterThan(
    				 root.<Date> get(criteria.getKey()).get("dateTime"), (Date)criteria.getValue());
        }else if(criteria.getOperation().equalsIgnoreCase("date<")) {
    		return builder.lessThan(
   				 root.<Date> get(criteria.getKey()).get("dateTime"), (Date)criteria.getValue());
       }else if(criteria.getOperation().equalsIgnoreCase("profile2")) {
   		return builder.equal(root.get(criteria.getKey())
				.get("profile2").get("profileId"), criteria.getValue());
       }else if(criteria.getOperation().equalsIgnoreCase("notNull")) {
      		return builder.isNotNull(root.get(criteria.getKey()));
       }else if(criteria.getOperation().equalsIgnoreCase("null")) {
     		return builder.isNull(root.get(criteria.getKey()));
      }
        
        return null;
    }
	
	


}
