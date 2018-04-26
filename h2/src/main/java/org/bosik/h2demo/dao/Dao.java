package org.bosik.h2demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Nikita Bosik
 * @since 2018-04-26
 */
public abstract class Dao<T>
{
	@PersistenceContext
	private EntityManager entityManager;

	public void create(T entity)
	{
		entityManager.persist(entity);
	}

	public T find(String id)
	{
		return entityManager.find(getEntityClass(), id);
	}

	public void update(T entity)
	{
		entityManager.merge(entity);
	}

	public boolean delete(String id)
	{
		T entity = find(id);
		if (entity != null)
		{
			entityManager.remove(entity);
			return true;
		}
		else
		{
			return false;
		}
	}

	public List<T> findAll()
	{
		return (List<T>) entityManager.createQuery("SELECT p FROM " + getEntityClass().getSimpleName() + " p").getResultList();
	}

	protected abstract Class<T> getEntityClass();
}
