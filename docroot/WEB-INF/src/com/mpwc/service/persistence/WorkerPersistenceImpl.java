/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.mpwc.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.mpwc.NoSuchWorkerException;

import com.mpwc.model.Worker;
import com.mpwc.model.impl.WorkerImpl;
import com.mpwc.model.impl.WorkerModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the worker service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author rsicart
 * @see WorkerPersistence
 * @see WorkerUtil
 * @generated
 */
public class WorkerPersistenceImpl extends BasePersistenceImpl<Worker>
	implements WorkerPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link WorkerUtil} to access the worker persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = WorkerImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_NIF = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByNif",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NIF = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByNif",
			new String[] { String.class.getName() },
			WorkerModelImpl.NIF_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NIF = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByNif",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_EMAIL = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEmail",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAIL = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEmail",
			new String[] { String.class.getName() },
			WorkerModelImpl.EMAIL_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_EMAIL = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEmail",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_NAME = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByName",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByName",
			new String[] { String.class.getName() },
			WorkerModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SURNAME = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySurname",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SURNAME =
		new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySurname",
			new String[] { String.class.getName() },
			WorkerModelImpl.SURNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SURNAME = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySurname",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NIF = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_Nif",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NIF = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_Nif",
			new String[] { Long.class.getName(), String.class.getName() },
			WorkerModelImpl.GROUPID_COLUMN_BITMASK |
			WorkerModelImpl.NIF_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_NIF = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_Nif",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_EMAIL = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_Email",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_EMAIL =
		new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_Email",
			new String[] { Long.class.getName(), String.class.getName() },
			WorkerModelImpl.GROUPID_COLUMN_BITMASK |
			WorkerModelImpl.EMAIL_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_EMAIL = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_Email",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NAME = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_Name",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NAME =
		new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_Name",
			new String[] { Long.class.getName(), String.class.getName() },
			WorkerModelImpl.GROUPID_COLUMN_BITMASK |
			WorkerModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_NAME = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_Name",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_SURNAME =
		new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_Surname",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_SURNAME =
		new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_Surname",
			new String[] { Long.class.getName(), String.class.getName() },
			WorkerModelImpl.GROUPID_COLUMN_BITMASK |
			WorkerModelImpl.SURNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_SURNAME = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_Surname",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			WorkerModelImpl.GROUPID_COLUMN_BITMASK |
			WorkerModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, WorkerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the worker in the entity cache if it is enabled.
	 *
	 * @param worker the worker
	 */
	public void cacheResult(Worker worker) {
		EntityCacheUtil.putResult(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerImpl.class, worker.getPrimaryKey(), worker);

		worker.resetOriginalValues();
	}

	/**
	 * Caches the workers in the entity cache if it is enabled.
	 *
	 * @param workers the workers
	 */
	public void cacheResult(List<Worker> workers) {
		for (Worker worker : workers) {
			if (EntityCacheUtil.getResult(
						WorkerModelImpl.ENTITY_CACHE_ENABLED, WorkerImpl.class,
						worker.getPrimaryKey()) == null) {
				cacheResult(worker);
			}
			else {
				worker.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all workers.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(WorkerImpl.class.getName());
		}

		EntityCacheUtil.clearCache(WorkerImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the worker.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Worker worker) {
		EntityCacheUtil.removeResult(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerImpl.class, worker.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Worker> workers) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Worker worker : workers) {
			EntityCacheUtil.removeResult(WorkerModelImpl.ENTITY_CACHE_ENABLED,
				WorkerImpl.class, worker.getPrimaryKey());
		}
	}

	/**
	 * Creates a new worker with the primary key. Does not add the worker to the database.
	 *
	 * @param workerId the primary key for the new worker
	 * @return the new worker
	 */
	public Worker create(long workerId) {
		Worker worker = new WorkerImpl();

		worker.setNew(true);
		worker.setPrimaryKey(workerId);

		return worker;
	}

	/**
	 * Removes the worker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workerId the primary key of the worker
	 * @return the worker that was removed
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker remove(long workerId)
		throws NoSuchWorkerException, SystemException {
		return remove(Long.valueOf(workerId));
	}

	/**
	 * Removes the worker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the worker
	 * @return the worker that was removed
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Worker remove(Serializable primaryKey)
		throws NoSuchWorkerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Worker worker = (Worker)session.get(WorkerImpl.class, primaryKey);

			if (worker == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchWorkerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(worker);
		}
		catch (NoSuchWorkerException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Worker removeImpl(Worker worker) throws SystemException {
		worker = toUnwrappedModel(worker);

		try {
			clearProjects.clear(worker.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, worker);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(worker);

		return worker;
	}

	@Override
	public Worker updateImpl(com.mpwc.model.Worker worker, boolean merge)
		throws SystemException {
		worker = toUnwrappedModel(worker);

		boolean isNew = worker.isNew();

		WorkerModelImpl workerModelImpl = (WorkerModelImpl)worker;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, worker, merge);

			worker.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !WorkerModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((workerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NIF.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { workerModelImpl.getOriginalNif() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NIF, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NIF,
					args);

				args = new Object[] { workerModelImpl.getNif() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NIF, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NIF,
					args);
			}

			if ((workerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAIL.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { workerModelImpl.getOriginalEmail() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_EMAIL, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAIL,
					args);

				args = new Object[] { workerModelImpl.getEmail() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_EMAIL, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAIL,
					args);
			}

			if ((workerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { workerModelImpl.getOriginalName() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME,
					args);

				args = new Object[] { workerModelImpl.getName() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME,
					args);
			}

			if ((workerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SURNAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						workerModelImpl.getOriginalSurname()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SURNAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SURNAME,
					args);

				args = new Object[] { workerModelImpl.getSurname() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SURNAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SURNAME,
					args);
			}

			if ((workerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NIF.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(workerModelImpl.getOriginalGroupId()),
						
						workerModelImpl.getOriginalNif()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_NIF, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NIF,
					args);

				args = new Object[] {
						Long.valueOf(workerModelImpl.getGroupId()),
						
						workerModelImpl.getNif()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_NIF, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NIF,
					args);
			}

			if ((workerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_EMAIL.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(workerModelImpl.getOriginalGroupId()),
						
						workerModelImpl.getOriginalEmail()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_EMAIL, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_EMAIL,
					args);

				args = new Object[] {
						Long.valueOf(workerModelImpl.getGroupId()),
						
						workerModelImpl.getEmail()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_EMAIL, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_EMAIL,
					args);
			}

			if ((workerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(workerModelImpl.getOriginalGroupId()),
						
						workerModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_NAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NAME,
					args);

				args = new Object[] {
						Long.valueOf(workerModelImpl.getGroupId()),
						
						workerModelImpl.getName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_NAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NAME,
					args);
			}

			if ((workerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_SURNAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(workerModelImpl.getOriginalGroupId()),
						
						workerModelImpl.getOriginalSurname()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_SURNAME,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_SURNAME,
					args);

				args = new Object[] {
						Long.valueOf(workerModelImpl.getGroupId()),
						
						workerModelImpl.getSurname()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_SURNAME,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_SURNAME,
					args);
			}

			if ((workerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(workerModelImpl.getOriginalGroupId()),
						Long.valueOf(workerModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);

				args = new Object[] {
						Long.valueOf(workerModelImpl.getGroupId()),
						Long.valueOf(workerModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);
			}
		}

		EntityCacheUtil.putResult(WorkerModelImpl.ENTITY_CACHE_ENABLED,
			WorkerImpl.class, worker.getPrimaryKey(), worker);

		return worker;
	}

	protected Worker toUnwrappedModel(Worker worker) {
		if (worker instanceof WorkerImpl) {
			return worker;
		}

		WorkerImpl workerImpl = new WorkerImpl();

		workerImpl.setNew(worker.isNew());
		workerImpl.setPrimaryKey(worker.getPrimaryKey());

		workerImpl.setWorkerId(worker.getWorkerId());
		workerImpl.setGroupId(worker.getGroupId());
		workerImpl.setCompanyId(worker.getCompanyId());
		workerImpl.setUserId(worker.getUserId());
		workerImpl.setUserName(worker.getUserName());
		workerImpl.setCreateDate(worker.getCreateDate());
		workerImpl.setModifiedDate(worker.getModifiedDate());
		workerImpl.setNif(worker.getNif());
		workerImpl.setName(worker.getName());
		workerImpl.setSurname(worker.getSurname());
		workerImpl.setEmail(worker.getEmail());
		workerImpl.setPhone(worker.getPhone());
		workerImpl.setComments(worker.getComments());
		workerImpl.setStatusId(worker.getStatusId());

		return workerImpl;
	}

	/**
	 * Returns the worker with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the worker
	 * @return the worker
	 * @throws com.liferay.portal.NoSuchModelException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Worker findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the worker with the primary key or throws a {@link com.mpwc.NoSuchWorkerException} if it could not be found.
	 *
	 * @param workerId the primary key of the worker
	 * @return the worker
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByPrimaryKey(long workerId)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByPrimaryKey(workerId);

		if (worker == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + workerId);
			}

			throw new NoSuchWorkerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				workerId);
		}

		return worker;
	}

	/**
	 * Returns the worker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the worker
	 * @return the worker, or <code>null</code> if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Worker fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the worker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param workerId the primary key of the worker
	 * @return the worker, or <code>null</code> if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByPrimaryKey(long workerId) throws SystemException {
		Worker worker = (Worker)EntityCacheUtil.getResult(WorkerModelImpl.ENTITY_CACHE_ENABLED,
				WorkerImpl.class, workerId);

		if (worker == _nullWorker) {
			return null;
		}

		if (worker == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				worker = (Worker)session.get(WorkerImpl.class,
						Long.valueOf(workerId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (worker != null) {
					cacheResult(worker);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(WorkerModelImpl.ENTITY_CACHE_ENABLED,
						WorkerImpl.class, workerId, _nullWorker);
				}

				closeSession(session);
			}
		}

		return worker;
	}

	/**
	 * Returns all the workers where nif = &#63;.
	 *
	 * @param nif the nif
	 * @return the matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByNif(String nif) throws SystemException {
		return findByNif(nif, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workers where nif = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param nif the nif
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @return the range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByNif(String nif, int start, int end)
		throws SystemException {
		return findByNif(nif, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workers where nif = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param nif the nif
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByNif(String nif, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NIF;
			finderArgs = new Object[] { nif };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_NIF;
			finderArgs = new Object[] { nif, start, end, orderByComparator };
		}

		List<Worker> list = (List<Worker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Worker worker : list) {
				if (!Validator.equals(nif, worker.getNif())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_WORKER_WHERE);

			if (nif == null) {
				query.append(_FINDER_COLUMN_NIF_NIF_1);
			}
			else {
				if (nif.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NIF_NIF_3);
				}
				else {
					query.append(_FINDER_COLUMN_NIF_NIF_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(WorkerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (nif != null) {
					qPos.add(nif);
				}

				list = (List<Worker>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first worker in the ordered set where nif = &#63;.
	 *
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByNif_First(String nif,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByNif_First(nif, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nif=");
		msg.append(nif);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the first worker in the ordered set where nif = &#63;.
	 *
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByNif_First(String nif,
		OrderByComparator orderByComparator) throws SystemException {
		List<Worker> list = findByNif(nif, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last worker in the ordered set where nif = &#63;.
	 *
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByNif_Last(String nif, OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByNif_Last(nif, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nif=");
		msg.append(nif);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the last worker in the ordered set where nif = &#63;.
	 *
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByNif_Last(String nif,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByNif(nif);

		List<Worker> list = findByNif(nif, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workers before and after the current worker in the ordered set where nif = &#63;.
	 *
	 * @param workerId the primary key of the current worker
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next worker
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker[] findByNif_PrevAndNext(long workerId, String nif,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = findByPrimaryKey(workerId);

		Session session = null;

		try {
			session = openSession();

			Worker[] array = new WorkerImpl[3];

			array[0] = getByNif_PrevAndNext(session, worker, nif,
					orderByComparator, true);

			array[1] = worker;

			array[2] = getByNif_PrevAndNext(session, worker, nif,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Worker getByNif_PrevAndNext(Session session, Worker worker,
		String nif, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WORKER_WHERE);

		if (nif == null) {
			query.append(_FINDER_COLUMN_NIF_NIF_1);
		}
		else {
			if (nif.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NIF_NIF_3);
			}
			else {
				query.append(_FINDER_COLUMN_NIF_NIF_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(WorkerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (nif != null) {
			qPos.add(nif);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(worker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Worker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the workers where email = &#63;.
	 *
	 * @param email the email
	 * @return the matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByEmail(String email) throws SystemException {
		return findByEmail(email, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workers where email = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param email the email
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @return the range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByEmail(String email, int start, int end)
		throws SystemException {
		return findByEmail(email, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workers where email = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param email the email
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByEmail(String email, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAIL;
			finderArgs = new Object[] { email };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_EMAIL;
			finderArgs = new Object[] { email, start, end, orderByComparator };
		}

		List<Worker> list = (List<Worker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Worker worker : list) {
				if (!Validator.equals(email, worker.getEmail())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_WORKER_WHERE);

			if (email == null) {
				query.append(_FINDER_COLUMN_EMAIL_EMAIL_1);
			}
			else {
				if (email.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_EMAIL_EMAIL_3);
				}
				else {
					query.append(_FINDER_COLUMN_EMAIL_EMAIL_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(WorkerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (email != null) {
					qPos.add(email);
				}

				list = (List<Worker>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first worker in the ordered set where email = &#63;.
	 *
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByEmail_First(String email,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByEmail_First(email, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("email=");
		msg.append(email);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the first worker in the ordered set where email = &#63;.
	 *
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByEmail_First(String email,
		OrderByComparator orderByComparator) throws SystemException {
		List<Worker> list = findByEmail(email, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last worker in the ordered set where email = &#63;.
	 *
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByEmail_Last(String email,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByEmail_Last(email, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("email=");
		msg.append(email);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the last worker in the ordered set where email = &#63;.
	 *
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByEmail_Last(String email,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByEmail(email);

		List<Worker> list = findByEmail(email, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workers before and after the current worker in the ordered set where email = &#63;.
	 *
	 * @param workerId the primary key of the current worker
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next worker
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker[] findByEmail_PrevAndNext(long workerId, String email,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = findByPrimaryKey(workerId);

		Session session = null;

		try {
			session = openSession();

			Worker[] array = new WorkerImpl[3];

			array[0] = getByEmail_PrevAndNext(session, worker, email,
					orderByComparator, true);

			array[1] = worker;

			array[2] = getByEmail_PrevAndNext(session, worker, email,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Worker getByEmail_PrevAndNext(Session session, Worker worker,
		String email, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WORKER_WHERE);

		if (email == null) {
			query.append(_FINDER_COLUMN_EMAIL_EMAIL_1);
		}
		else {
			if (email.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_EMAIL_EMAIL_3);
			}
			else {
				query.append(_FINDER_COLUMN_EMAIL_EMAIL_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(WorkerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (email != null) {
			qPos.add(email);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(worker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Worker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the workers where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByName(String name) throws SystemException {
		return findByName(name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workers where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @return the range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByName(String name, int start, int end)
		throws SystemException {
		return findByName(name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workers where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByName(String name, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME;
			finderArgs = new Object[] { name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_NAME;
			finderArgs = new Object[] { name, start, end, orderByComparator };
		}

		List<Worker> list = (List<Worker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Worker worker : list) {
				if (!Validator.equals(name, worker.getName())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_WORKER_WHERE);

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NAME_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_NAME_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(WorkerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<Worker>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first worker in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByName_First(String name,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByName_First(name, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the first worker in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByName_First(String name,
		OrderByComparator orderByComparator) throws SystemException {
		List<Worker> list = findByName(name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last worker in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByName_Last(String name,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByName_Last(name, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the last worker in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByName_Last(String name,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByName(name);

		List<Worker> list = findByName(name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workers before and after the current worker in the ordered set where name = &#63;.
	 *
	 * @param workerId the primary key of the current worker
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next worker
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker[] findByName_PrevAndNext(long workerId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = findByPrimaryKey(workerId);

		Session session = null;

		try {
			session = openSession();

			Worker[] array = new WorkerImpl[3];

			array[0] = getByName_PrevAndNext(session, worker, name,
					orderByComparator, true);

			array[1] = worker;

			array[2] = getByName_PrevAndNext(session, worker, name,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Worker getByName_PrevAndNext(Session session, Worker worker,
		String name, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WORKER_WHERE);

		if (name == null) {
			query.append(_FINDER_COLUMN_NAME_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(WorkerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(worker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Worker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the workers where surname = &#63;.
	 *
	 * @param surname the surname
	 * @return the matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findBySurname(String surname) throws SystemException {
		return findBySurname(surname, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workers where surname = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param surname the surname
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @return the range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findBySurname(String surname, int start, int end)
		throws SystemException {
		return findBySurname(surname, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workers where surname = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param surname the surname
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findBySurname(String surname, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SURNAME;
			finderArgs = new Object[] { surname };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SURNAME;
			finderArgs = new Object[] { surname, start, end, orderByComparator };
		}

		List<Worker> list = (List<Worker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Worker worker : list) {
				if (!Validator.equals(surname, worker.getSurname())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_WORKER_WHERE);

			if (surname == null) {
				query.append(_FINDER_COLUMN_SURNAME_SURNAME_1);
			}
			else {
				if (surname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_SURNAME_SURNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_SURNAME_SURNAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(WorkerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (surname != null) {
					qPos.add(surname);
				}

				list = (List<Worker>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first worker in the ordered set where surname = &#63;.
	 *
	 * @param surname the surname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findBySurname_First(String surname,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchBySurname_First(surname, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("surname=");
		msg.append(surname);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the first worker in the ordered set where surname = &#63;.
	 *
	 * @param surname the surname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchBySurname_First(String surname,
		OrderByComparator orderByComparator) throws SystemException {
		List<Worker> list = findBySurname(surname, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last worker in the ordered set where surname = &#63;.
	 *
	 * @param surname the surname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findBySurname_Last(String surname,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchBySurname_Last(surname, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("surname=");
		msg.append(surname);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the last worker in the ordered set where surname = &#63;.
	 *
	 * @param surname the surname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchBySurname_Last(String surname,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countBySurname(surname);

		List<Worker> list = findBySurname(surname, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workers before and after the current worker in the ordered set where surname = &#63;.
	 *
	 * @param workerId the primary key of the current worker
	 * @param surname the surname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next worker
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker[] findBySurname_PrevAndNext(long workerId, String surname,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = findByPrimaryKey(workerId);

		Session session = null;

		try {
			session = openSession();

			Worker[] array = new WorkerImpl[3];

			array[0] = getBySurname_PrevAndNext(session, worker, surname,
					orderByComparator, true);

			array[1] = worker;

			array[2] = getBySurname_PrevAndNext(session, worker, surname,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Worker getBySurname_PrevAndNext(Session session, Worker worker,
		String surname, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WORKER_WHERE);

		if (surname == null) {
			query.append(_FINDER_COLUMN_SURNAME_SURNAME_1);
		}
		else {
			if (surname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SURNAME_SURNAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_SURNAME_SURNAME_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(WorkerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (surname != null) {
			qPos.add(surname);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(worker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Worker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the workers where groupId = &#63; and nif = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nif the nif
	 * @return the matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Nif(long groupId, String nif)
		throws SystemException {
		return findByG_Nif(groupId, nif, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the workers where groupId = &#63; and nif = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nif the nif
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @return the range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Nif(long groupId, String nif, int start, int end)
		throws SystemException {
		return findByG_Nif(groupId, nif, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workers where groupId = &#63; and nif = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nif the nif
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Nif(long groupId, String nif, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NIF;
			finderArgs = new Object[] { groupId, nif };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NIF;
			finderArgs = new Object[] {
					groupId, nif,
					
					start, end, orderByComparator
				};
		}

		List<Worker> list = (List<Worker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Worker worker : list) {
				if ((groupId != worker.getGroupId()) ||
						!Validator.equals(nif, worker.getNif())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_WORKER_WHERE);

			query.append(_FINDER_COLUMN_G_NIF_GROUPID_2);

			if (nif == null) {
				query.append(_FINDER_COLUMN_G_NIF_NIF_1);
			}
			else {
				if (nif.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_NIF_NIF_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_NIF_NIF_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(WorkerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (nif != null) {
					qPos.add(nif);
				}

				list = (List<Worker>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first worker in the ordered set where groupId = &#63; and nif = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByG_Nif_First(long groupId, String nif,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByG_Nif_First(groupId, nif, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nif=");
		msg.append(nif);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the first worker in the ordered set where groupId = &#63; and nif = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByG_Nif_First(long groupId, String nif,
		OrderByComparator orderByComparator) throws SystemException {
		List<Worker> list = findByG_Nif(groupId, nif, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last worker in the ordered set where groupId = &#63; and nif = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByG_Nif_Last(long groupId, String nif,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByG_Nif_Last(groupId, nif, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nif=");
		msg.append(nif);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the last worker in the ordered set where groupId = &#63; and nif = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByG_Nif_Last(long groupId, String nif,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_Nif(groupId, nif);

		List<Worker> list = findByG_Nif(groupId, nif, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workers before and after the current worker in the ordered set where groupId = &#63; and nif = &#63;.
	 *
	 * @param workerId the primary key of the current worker
	 * @param groupId the group ID
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next worker
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker[] findByG_Nif_PrevAndNext(long workerId, long groupId,
		String nif, OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = findByPrimaryKey(workerId);

		Session session = null;

		try {
			session = openSession();

			Worker[] array = new WorkerImpl[3];

			array[0] = getByG_Nif_PrevAndNext(session, worker, groupId, nif,
					orderByComparator, true);

			array[1] = worker;

			array[2] = getByG_Nif_PrevAndNext(session, worker, groupId, nif,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Worker getByG_Nif_PrevAndNext(Session session, Worker worker,
		long groupId, String nif, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WORKER_WHERE);

		query.append(_FINDER_COLUMN_G_NIF_GROUPID_2);

		if (nif == null) {
			query.append(_FINDER_COLUMN_G_NIF_NIF_1);
		}
		else {
			if (nif.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_NIF_NIF_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_NIF_NIF_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(WorkerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (nif != null) {
			qPos.add(nif);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(worker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Worker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the workers where groupId = &#63; and email = &#63;.
	 *
	 * @param groupId the group ID
	 * @param email the email
	 * @return the matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Email(long groupId, String email)
		throws SystemException {
		return findByG_Email(groupId, email, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workers where groupId = &#63; and email = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param email the email
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @return the range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Email(long groupId, String email, int start,
		int end) throws SystemException {
		return findByG_Email(groupId, email, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workers where groupId = &#63; and email = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param email the email
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Email(long groupId, String email, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_EMAIL;
			finderArgs = new Object[] { groupId, email };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_EMAIL;
			finderArgs = new Object[] {
					groupId, email,
					
					start, end, orderByComparator
				};
		}

		List<Worker> list = (List<Worker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Worker worker : list) {
				if ((groupId != worker.getGroupId()) ||
						!Validator.equals(email, worker.getEmail())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_WORKER_WHERE);

			query.append(_FINDER_COLUMN_G_EMAIL_GROUPID_2);

			if (email == null) {
				query.append(_FINDER_COLUMN_G_EMAIL_EMAIL_1);
			}
			else {
				if (email.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_EMAIL_EMAIL_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_EMAIL_EMAIL_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(WorkerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (email != null) {
					qPos.add(email);
				}

				list = (List<Worker>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first worker in the ordered set where groupId = &#63; and email = &#63;.
	 *
	 * @param groupId the group ID
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByG_Email_First(long groupId, String email,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByG_Email_First(groupId, email, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", email=");
		msg.append(email);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the first worker in the ordered set where groupId = &#63; and email = &#63;.
	 *
	 * @param groupId the group ID
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByG_Email_First(long groupId, String email,
		OrderByComparator orderByComparator) throws SystemException {
		List<Worker> list = findByG_Email(groupId, email, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last worker in the ordered set where groupId = &#63; and email = &#63;.
	 *
	 * @param groupId the group ID
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByG_Email_Last(long groupId, String email,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByG_Email_Last(groupId, email, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", email=");
		msg.append(email);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the last worker in the ordered set where groupId = &#63; and email = &#63;.
	 *
	 * @param groupId the group ID
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByG_Email_Last(long groupId, String email,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_Email(groupId, email);

		List<Worker> list = findByG_Email(groupId, email, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workers before and after the current worker in the ordered set where groupId = &#63; and email = &#63;.
	 *
	 * @param workerId the primary key of the current worker
	 * @param groupId the group ID
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next worker
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker[] findByG_Email_PrevAndNext(long workerId, long groupId,
		String email, OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = findByPrimaryKey(workerId);

		Session session = null;

		try {
			session = openSession();

			Worker[] array = new WorkerImpl[3];

			array[0] = getByG_Email_PrevAndNext(session, worker, groupId,
					email, orderByComparator, true);

			array[1] = worker;

			array[2] = getByG_Email_PrevAndNext(session, worker, groupId,
					email, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Worker getByG_Email_PrevAndNext(Session session, Worker worker,
		long groupId, String email, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WORKER_WHERE);

		query.append(_FINDER_COLUMN_G_EMAIL_GROUPID_2);

		if (email == null) {
			query.append(_FINDER_COLUMN_G_EMAIL_EMAIL_1);
		}
		else {
			if (email.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_EMAIL_EMAIL_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_EMAIL_EMAIL_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(WorkerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (email != null) {
			qPos.add(email);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(worker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Worker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the workers where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Name(long groupId, String name)
		throws SystemException {
		return findByG_Name(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workers where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @return the range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Name(long groupId, String name, int start,
		int end) throws SystemException {
		return findByG_Name(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workers where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Name(long groupId, String name, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NAME;
			finderArgs = new Object[] { groupId, name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NAME;
			finderArgs = new Object[] {
					groupId, name,
					
					start, end, orderByComparator
				};
		}

		List<Worker> list = (List<Worker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Worker worker : list) {
				if ((groupId != worker.getGroupId()) ||
						!Validator.equals(name, worker.getName())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_WORKER_WHERE);

			query.append(_FINDER_COLUMN_G_NAME_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_NAME_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_NAME_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_NAME_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(WorkerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<Worker>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first worker in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByG_Name_First(long groupId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByG_Name_First(groupId, name, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the first worker in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByG_Name_First(long groupId, String name,
		OrderByComparator orderByComparator) throws SystemException {
		List<Worker> list = findByG_Name(groupId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last worker in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByG_Name_Last(long groupId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByG_Name_Last(groupId, name, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the last worker in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByG_Name_Last(long groupId, String name,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_Name(groupId, name);

		List<Worker> list = findByG_Name(groupId, name, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workers before and after the current worker in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param workerId the primary key of the current worker
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next worker
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker[] findByG_Name_PrevAndNext(long workerId, long groupId,
		String name, OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = findByPrimaryKey(workerId);

		Session session = null;

		try {
			session = openSession();

			Worker[] array = new WorkerImpl[3];

			array[0] = getByG_Name_PrevAndNext(session, worker, groupId, name,
					orderByComparator, true);

			array[1] = worker;

			array[2] = getByG_Name_PrevAndNext(session, worker, groupId, name,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Worker getByG_Name_PrevAndNext(Session session, Worker worker,
		long groupId, String name, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WORKER_WHERE);

		query.append(_FINDER_COLUMN_G_NAME_GROUPID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_G_NAME_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_NAME_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_NAME_NAME_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(WorkerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(worker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Worker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the workers where groupId = &#63; and surname = &#63;.
	 *
	 * @param groupId the group ID
	 * @param surname the surname
	 * @return the matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Surname(long groupId, String surname)
		throws SystemException {
		return findByG_Surname(groupId, surname, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workers where groupId = &#63; and surname = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param surname the surname
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @return the range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Surname(long groupId, String surname,
		int start, int end) throws SystemException {
		return findByG_Surname(groupId, surname, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workers where groupId = &#63; and surname = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param surname the surname
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_Surname(long groupId, String surname,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_SURNAME;
			finderArgs = new Object[] { groupId, surname };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_SURNAME;
			finderArgs = new Object[] {
					groupId, surname,
					
					start, end, orderByComparator
				};
		}

		List<Worker> list = (List<Worker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Worker worker : list) {
				if ((groupId != worker.getGroupId()) ||
						!Validator.equals(surname, worker.getSurname())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_WORKER_WHERE);

			query.append(_FINDER_COLUMN_G_SURNAME_GROUPID_2);

			if (surname == null) {
				query.append(_FINDER_COLUMN_G_SURNAME_SURNAME_1);
			}
			else {
				if (surname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_SURNAME_SURNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_SURNAME_SURNAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(WorkerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (surname != null) {
					qPos.add(surname);
				}

				list = (List<Worker>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first worker in the ordered set where groupId = &#63; and surname = &#63;.
	 *
	 * @param groupId the group ID
	 * @param surname the surname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByG_Surname_First(long groupId, String surname,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByG_Surname_First(groupId, surname,
				orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", surname=");
		msg.append(surname);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the first worker in the ordered set where groupId = &#63; and surname = &#63;.
	 *
	 * @param groupId the group ID
	 * @param surname the surname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByG_Surname_First(long groupId, String surname,
		OrderByComparator orderByComparator) throws SystemException {
		List<Worker> list = findByG_Surname(groupId, surname, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last worker in the ordered set where groupId = &#63; and surname = &#63;.
	 *
	 * @param groupId the group ID
	 * @param surname the surname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByG_Surname_Last(long groupId, String surname,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByG_Surname_Last(groupId, surname,
				orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", surname=");
		msg.append(surname);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the last worker in the ordered set where groupId = &#63; and surname = &#63;.
	 *
	 * @param groupId the group ID
	 * @param surname the surname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByG_Surname_Last(long groupId, String surname,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_Surname(groupId, surname);

		List<Worker> list = findByG_Surname(groupId, surname, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workers before and after the current worker in the ordered set where groupId = &#63; and surname = &#63;.
	 *
	 * @param workerId the primary key of the current worker
	 * @param groupId the group ID
	 * @param surname the surname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next worker
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker[] findByG_Surname_PrevAndNext(long workerId, long groupId,
		String surname, OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = findByPrimaryKey(workerId);

		Session session = null;

		try {
			session = openSession();

			Worker[] array = new WorkerImpl[3];

			array[0] = getByG_Surname_PrevAndNext(session, worker, groupId,
					surname, orderByComparator, true);

			array[1] = worker;

			array[2] = getByG_Surname_PrevAndNext(session, worker, groupId,
					surname, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Worker getByG_Surname_PrevAndNext(Session session, Worker worker,
		long groupId, String surname, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WORKER_WHERE);

		query.append(_FINDER_COLUMN_G_SURNAME_GROUPID_2);

		if (surname == null) {
			query.append(_FINDER_COLUMN_G_SURNAME_SURNAME_1);
		}
		else {
			if (surname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_SURNAME_SURNAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_SURNAME_SURNAME_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(WorkerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (surname != null) {
			qPos.add(surname);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(worker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Worker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the workers where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_U(long groupId, long userId)
		throws SystemException {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the workers where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @return the range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_U(long groupId, long userId, int start, int end)
		throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the workers where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] { groupId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] {
					groupId, userId,
					
					start, end, orderByComparator
				};
		}

		List<Worker> list = (List<Worker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Worker worker : list) {
				if ((groupId != worker.getGroupId()) ||
						(userId != worker.getUserId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_WORKER_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(WorkerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<Worker>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first worker in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByG_U_First(groupId, userId, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the first worker in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Worker> list = findByG_U(groupId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last worker in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker
	 * @throws com.mpwc.NoSuchWorkerException if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker findByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = fetchByG_U_Last(groupId, userId, orderByComparator);

		if (worker != null) {
			return worker;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWorkerException(msg.toString());
	}

	/**
	 * Returns the last worker in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching worker, or <code>null</code> if a matching worker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker fetchByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_U(groupId, userId);

		List<Worker> list = findByG_U(groupId, userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the workers before and after the current worker in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param workerId the primary key of the current worker
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next worker
	 * @throws com.mpwc.NoSuchWorkerException if a worker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Worker[] findByG_U_PrevAndNext(long workerId, long groupId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchWorkerException, SystemException {
		Worker worker = findByPrimaryKey(workerId);

		Session session = null;

		try {
			session = openSession();

			Worker[] array = new WorkerImpl[3];

			array[0] = getByG_U_PrevAndNext(session, worker, groupId, userId,
					orderByComparator, true);

			array[1] = worker;

			array[2] = getByG_U_PrevAndNext(session, worker, groupId, userId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Worker getByG_U_PrevAndNext(Session session, Worker worker,
		long groupId, long userId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WORKER_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(WorkerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(worker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Worker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the workers.
	 *
	 * @return the workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the workers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @return the range of workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the workers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of workers
	 * @throws SystemException if a system exception occurred
	 */
	public List<Worker> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Worker> list = (List<Worker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_WORKER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WORKER.concat(WorkerModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Worker>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Worker>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the workers where nif = &#63; from the database.
	 *
	 * @param nif the nif
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByNif(String nif) throws SystemException {
		for (Worker worker : findByNif(nif)) {
			remove(worker);
		}
	}

	/**
	 * Removes all the workers where email = &#63; from the database.
	 *
	 * @param email the email
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByEmail(String email) throws SystemException {
		for (Worker worker : findByEmail(email)) {
			remove(worker);
		}
	}

	/**
	 * Removes all the workers where name = &#63; from the database.
	 *
	 * @param name the name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByName(String name) throws SystemException {
		for (Worker worker : findByName(name)) {
			remove(worker);
		}
	}

	/**
	 * Removes all the workers where surname = &#63; from the database.
	 *
	 * @param surname the surname
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBySurname(String surname) throws SystemException {
		for (Worker worker : findBySurname(surname)) {
			remove(worker);
		}
	}

	/**
	 * Removes all the workers where groupId = &#63; and nif = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param nif the nif
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_Nif(long groupId, String nif)
		throws SystemException {
		for (Worker worker : findByG_Nif(groupId, nif)) {
			remove(worker);
		}
	}

	/**
	 * Removes all the workers where groupId = &#63; and email = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param email the email
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_Email(long groupId, String email)
		throws SystemException {
		for (Worker worker : findByG_Email(groupId, email)) {
			remove(worker);
		}
	}

	/**
	 * Removes all the workers where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_Name(long groupId, String name)
		throws SystemException {
		for (Worker worker : findByG_Name(groupId, name)) {
			remove(worker);
		}
	}

	/**
	 * Removes all the workers where groupId = &#63; and surname = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param surname the surname
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_Surname(long groupId, String surname)
		throws SystemException {
		for (Worker worker : findByG_Surname(groupId, surname)) {
			remove(worker);
		}
	}

	/**
	 * Removes all the workers where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (Worker worker : findByG_U(groupId, userId)) {
			remove(worker);
		}
	}

	/**
	 * Removes all the workers from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Worker worker : findAll()) {
			remove(worker);
		}
	}

	/**
	 * Returns the number of workers where nif = &#63;.
	 *
	 * @param nif the nif
	 * @return the number of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByNif(String nif) throws SystemException {
		Object[] finderArgs = new Object[] { nif };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NIF,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WORKER_WHERE);

			if (nif == null) {
				query.append(_FINDER_COLUMN_NIF_NIF_1);
			}
			else {
				if (nif.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NIF_NIF_3);
				}
				else {
					query.append(_FINDER_COLUMN_NIF_NIF_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (nif != null) {
					qPos.add(nif);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NIF, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of workers where email = &#63;.
	 *
	 * @param email the email
	 * @return the number of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByEmail(String email) throws SystemException {
		Object[] finderArgs = new Object[] { email };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_EMAIL,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WORKER_WHERE);

			if (email == null) {
				query.append(_FINDER_COLUMN_EMAIL_EMAIL_1);
			}
			else {
				if (email.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_EMAIL_EMAIL_3);
				}
				else {
					query.append(_FINDER_COLUMN_EMAIL_EMAIL_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (email != null) {
					qPos.add(email);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_EMAIL,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of workers where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByName(String name) throws SystemException {
		Object[] finderArgs = new Object[] { name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NAME,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WORKER_WHERE);

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NAME_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_NAME_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of workers where surname = &#63;.
	 *
	 * @param surname the surname
	 * @return the number of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public int countBySurname(String surname) throws SystemException {
		Object[] finderArgs = new Object[] { surname };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SURNAME,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WORKER_WHERE);

			if (surname == null) {
				query.append(_FINDER_COLUMN_SURNAME_SURNAME_1);
			}
			else {
				if (surname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_SURNAME_SURNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_SURNAME_SURNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (surname != null) {
					qPos.add(surname);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SURNAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of workers where groupId = &#63; and nif = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nif the nif
	 * @return the number of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_Nif(long groupId, String nif) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, nif };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_NIF,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKER_WHERE);

			query.append(_FINDER_COLUMN_G_NIF_GROUPID_2);

			if (nif == null) {
				query.append(_FINDER_COLUMN_G_NIF_NIF_1);
			}
			else {
				if (nif.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_NIF_NIF_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_NIF_NIF_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (nif != null) {
					qPos.add(nif);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_NIF,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of workers where groupId = &#63; and email = &#63;.
	 *
	 * @param groupId the group ID
	 * @param email the email
	 * @return the number of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_Email(long groupId, String email)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, email };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_EMAIL,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKER_WHERE);

			query.append(_FINDER_COLUMN_G_EMAIL_GROUPID_2);

			if (email == null) {
				query.append(_FINDER_COLUMN_G_EMAIL_EMAIL_1);
			}
			else {
				if (email.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_EMAIL_EMAIL_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_EMAIL_EMAIL_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (email != null) {
					qPos.add(email);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_EMAIL,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of workers where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_Name(long groupId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_NAME,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKER_WHERE);

			query.append(_FINDER_COLUMN_G_NAME_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_NAME_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_NAME_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_NAME_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_NAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of workers where groupId = &#63; and surname = &#63;.
	 *
	 * @param groupId the group ID
	 * @param surname the surname
	 * @return the number of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_Surname(long groupId, String surname)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, surname };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_SURNAME,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKER_WHERE);

			query.append(_FINDER_COLUMN_G_SURNAME_GROUPID_2);

			if (surname == null) {
				query.append(_FINDER_COLUMN_G_SURNAME_SURNAME_1);
			}
			else {
				if (surname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_SURNAME_SURNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_SURNAME_SURNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (surname != null) {
					qPos.add(surname);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_SURNAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of workers where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching workers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WORKER_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of workers.
	 *
	 * @return the number of workers
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_WORKER);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns all the projects associated with the worker.
	 *
	 * @param pk the primary key of the worker
	 * @return the projects associated with the worker
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Project> getProjects(long pk)
		throws SystemException {
		return getProjects(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the projects associated with the worker.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the worker
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @return the range of projects associated with the worker
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Project> getProjects(long pk, int start, int end)
		throws SystemException {
		return getProjects(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_PROJECTS = new FinderPath(com.mpwc.model.impl.ProjectModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED_TOOLS_TBL_MPWC_WORKER_PROJECT,
			com.mpwc.model.impl.ProjectImpl.class,
			WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME,
			"getProjects",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_PROJECTS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the projects associated with the worker.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the worker
	 * @param start the lower bound of the range of workers
	 * @param end the upper bound of the range of workers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of projects associated with the worker
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Project> getProjects(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.mpwc.model.Project> list = (List<com.mpwc.model.Project>)FinderCacheUtil.getResult(FINDER_PATH_GET_PROJECTS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETPROJECTS.concat(ORDER_BY_CLAUSE)
										  .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETPROJECTS.concat(com.mpwc.model.impl.ProjectModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("tbl_mpwc_projects",
					com.mpwc.model.impl.ProjectImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.mpwc.model.Project>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_PROJECTS,
						finderArgs);
				}
				else {
					projectPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_PROJECTS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_PROJECTS_SIZE = new FinderPath(com.mpwc.model.impl.ProjectModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED_TOOLS_TBL_MPWC_WORKER_PROJECT,
			Long.class,
			WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME,
			"getProjectsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_PROJECTS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of projects associated with the worker.
	 *
	 * @param pk the primary key of the worker
	 * @return the number of projects associated with the worker
	 * @throws SystemException if a system exception occurred
	 */
	public int getProjectsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_PROJECTS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETPROJECTSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_PROJECTS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_PROJECT = new FinderPath(com.mpwc.model.impl.ProjectModelImpl.ENTITY_CACHE_ENABLED,
			WorkerModelImpl.FINDER_CACHE_ENABLED_TOOLS_TBL_MPWC_WORKER_PROJECT,
			Boolean.class,
			WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME,
			"containsProject",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the project is associated with the worker.
	 *
	 * @param pk the primary key of the worker
	 * @param projectPK the primary key of the project
	 * @return <code>true</code> if the project is associated with the worker; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsProject(long pk, long projectPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, projectPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_PROJECT,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsProject.contains(pk, projectPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_PROJECT,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the worker has any projects associated with it.
	 *
	 * @param pk the primary key of the worker to check for associations with projects
	 * @return <code>true</code> if the worker has any projects associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsProjects(long pk) throws SystemException {
		if (getProjectsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the worker and the project. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the worker
	 * @param projectPK the primary key of the project
	 * @throws SystemException if a system exception occurred
	 */
	public void addProject(long pk, long projectPK) throws SystemException {
		try {
			addProject.add(pk, projectPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Adds an association between the worker and the project. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the worker
	 * @param project the project
	 * @throws SystemException if a system exception occurred
	 */
	public void addProject(long pk, com.mpwc.model.Project project)
		throws SystemException {
		try {
			addProject.add(pk, project.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Adds an association between the worker and the projects. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the worker
	 * @param projectPKs the primary keys of the projects
	 * @throws SystemException if a system exception occurred
	 */
	public void addProjects(long pk, long[] projectPKs)
		throws SystemException {
		try {
			for (long projectPK : projectPKs) {
				addProject.add(pk, projectPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Adds an association between the worker and the projects. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the worker
	 * @param projects the projects
	 * @throws SystemException if a system exception occurred
	 */
	public void addProjects(long pk, List<com.mpwc.model.Project> projects)
		throws SystemException {
		try {
			for (com.mpwc.model.Project project : projects) {
				addProject.add(pk, project.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Clears all associations between the worker and its projects. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the worker to clear the associated projects from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearProjects(long pk) throws SystemException {
		try {
			clearProjects.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Removes the association between the worker and the project. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the worker
	 * @param projectPK the primary key of the project
	 * @throws SystemException if a system exception occurred
	 */
	public void removeProject(long pk, long projectPK)
		throws SystemException {
		try {
			removeProject.remove(pk, projectPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Removes the association between the worker and the project. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the worker
	 * @param project the project
	 * @throws SystemException if a system exception occurred
	 */
	public void removeProject(long pk, com.mpwc.model.Project project)
		throws SystemException {
		try {
			removeProject.remove(pk, project.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Removes the association between the worker and the projects. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the worker
	 * @param projectPKs the primary keys of the projects
	 * @throws SystemException if a system exception occurred
	 */
	public void removeProjects(long pk, long[] projectPKs)
		throws SystemException {
		try {
			for (long projectPK : projectPKs) {
				removeProject.remove(pk, projectPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Removes the association between the worker and the projects. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the worker
	 * @param projects the projects
	 * @throws SystemException if a system exception occurred
	 */
	public void removeProjects(long pk, List<com.mpwc.model.Project> projects)
		throws SystemException {
		try {
			for (com.mpwc.model.Project project : projects) {
				removeProject.remove(pk, project.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Sets the projects associated with the worker, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the worker
	 * @param projectPKs the primary keys of the projects to be associated with the worker
	 * @throws SystemException if a system exception occurred
	 */
	public void setProjects(long pk, long[] projectPKs)
		throws SystemException {
		try {
			Set<Long> projectPKSet = SetUtil.fromArray(projectPKs);

			List<com.mpwc.model.Project> projects = getProjects(pk);

			for (com.mpwc.model.Project project : projects) {
				if (!projectPKSet.remove(project.getPrimaryKey())) {
					removeProject.remove(pk, project.getPrimaryKey());
				}
			}

			for (Long projectPK : projectPKSet) {
				addProject.add(pk, projectPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Sets the projects associated with the worker, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the worker
	 * @param projects the projects to be associated with the worker
	 * @throws SystemException if a system exception occurred
	 */
	public void setProjects(long pk, List<com.mpwc.model.Project> projects)
		throws SystemException {
		try {
			long[] projectPKs = new long[projects.size()];

			for (int i = 0; i < projects.size(); i++) {
				com.mpwc.model.Project project = projects.get(i);

				projectPKs[i] = project.getPrimaryKey();
			}

			setProjects(pk, projectPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(WorkerModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Initializes the worker persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.mpwc.model.Worker")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Worker>> listenersList = new ArrayList<ModelListener<Worker>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Worker>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsProject = new ContainsProject();

		addProject = new AddProject();
		clearProjects = new ClearProjects();
		removeProject = new RemoveProject();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(WorkerImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = ProjectPersistence.class)
	protected ProjectPersistence projectPersistence;
	@BeanReference(type = ProjectStatusPersistence.class)
	protected ProjectStatusPersistence projectStatusPersistence;
	@BeanReference(type = StatusPersistence.class)
	protected StatusPersistence statusPersistence;
	@BeanReference(type = WorkerPersistence.class)
	protected WorkerPersistence workerPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	protected ContainsProject containsProject;
	protected AddProject addProject;
	protected ClearProjects clearProjects;
	protected RemoveProject removeProject;

	protected class ContainsProject {
		protected ContainsProject() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSPROJECT,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long workerId, long projectId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(workerId), new Long(projectId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddProject {
		protected AddProject() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO tools_tbl_mpwc_worker_project (workerId, projectId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long workerId, long projectId)
			throws SystemException {
			if (!containsProject.contains(workerId, projectId)) {
				ModelListener<com.mpwc.model.Project>[] projectListeners = projectPersistence.getListeners();

				for (ModelListener<Worker> listener : listeners) {
					listener.onBeforeAddAssociation(workerId,
						com.mpwc.model.Project.class.getName(), projectId);
				}

				for (ModelListener<com.mpwc.model.Project> listener : projectListeners) {
					listener.onBeforeAddAssociation(projectId,
						Worker.class.getName(), workerId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(workerId), new Long(projectId)
					});

				for (ModelListener<Worker> listener : listeners) {
					listener.onAfterAddAssociation(workerId,
						com.mpwc.model.Project.class.getName(), projectId);
				}

				for (ModelListener<com.mpwc.model.Project> listener : projectListeners) {
					listener.onAfterAddAssociation(projectId,
						Worker.class.getName(), workerId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearProjects {
		protected ClearProjects() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM tools_tbl_mpwc_worker_project WHERE workerId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long workerId) throws SystemException {
			ModelListener<com.mpwc.model.Project>[] projectListeners = projectPersistence.getListeners();

			List<com.mpwc.model.Project> projects = null;

			if ((listeners.length > 0) || (projectListeners.length > 0)) {
				projects = getProjects(workerId);

				for (com.mpwc.model.Project project : projects) {
					for (ModelListener<Worker> listener : listeners) {
						listener.onBeforeRemoveAssociation(workerId,
							com.mpwc.model.Project.class.getName(),
							project.getPrimaryKey());
					}

					for (ModelListener<com.mpwc.model.Project> listener : projectListeners) {
						listener.onBeforeRemoveAssociation(project.getPrimaryKey(),
							Worker.class.getName(), workerId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(workerId) });

			if ((listeners.length > 0) || (projectListeners.length > 0)) {
				for (com.mpwc.model.Project project : projects) {
					for (ModelListener<Worker> listener : listeners) {
						listener.onAfterRemoveAssociation(workerId,
							com.mpwc.model.Project.class.getName(),
							project.getPrimaryKey());
					}

					for (ModelListener<com.mpwc.model.Project> listener : projectListeners) {
						listener.onAfterRemoveAssociation(project.getPrimaryKey(),
							Worker.class.getName(), workerId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveProject {
		protected RemoveProject() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM tools_tbl_mpwc_worker_project WHERE workerId = ? AND projectId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long workerId, long projectId)
			throws SystemException {
			if (containsProject.contains(workerId, projectId)) {
				ModelListener<com.mpwc.model.Project>[] projectListeners = projectPersistence.getListeners();

				for (ModelListener<Worker> listener : listeners) {
					listener.onBeforeRemoveAssociation(workerId,
						com.mpwc.model.Project.class.getName(), projectId);
				}

				for (ModelListener<com.mpwc.model.Project> listener : projectListeners) {
					listener.onBeforeRemoveAssociation(projectId,
						Worker.class.getName(), workerId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(workerId), new Long(projectId)
					});

				for (ModelListener<Worker> listener : listeners) {
					listener.onAfterRemoveAssociation(workerId,
						com.mpwc.model.Project.class.getName(), projectId);
				}

				for (ModelListener<com.mpwc.model.Project> listener : projectListeners) {
					listener.onAfterRemoveAssociation(projectId,
						Worker.class.getName(), workerId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_WORKER = "SELECT worker FROM Worker worker";
	private static final String _SQL_SELECT_WORKER_WHERE = "SELECT worker FROM Worker worker WHERE ";
	private static final String _SQL_COUNT_WORKER = "SELECT COUNT(worker) FROM Worker worker";
	private static final String _SQL_COUNT_WORKER_WHERE = "SELECT COUNT(worker) FROM Worker worker WHERE ";
	private static final String _SQL_GETPROJECTS = "SELECT {tbl_mpwc_projects.*} FROM tbl_mpwc_projects INNER JOIN tools_tbl_mpwc_worker_project ON (tools_tbl_mpwc_worker_project.projectId = tbl_mpwc_projects.projectId) WHERE (tools_tbl_mpwc_worker_project.workerId = ?)";
	private static final String _SQL_GETPROJECTSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM tools_tbl_mpwc_worker_project WHERE workerId = ?";
	private static final String _SQL_CONTAINSPROJECT = "SELECT COUNT(*) AS COUNT_VALUE FROM tools_tbl_mpwc_worker_project WHERE workerId = ? AND projectId = ?";
	private static final String _FINDER_COLUMN_NIF_NIF_1 = "worker.nif IS NULL";
	private static final String _FINDER_COLUMN_NIF_NIF_2 = "worker.nif = ?";
	private static final String _FINDER_COLUMN_NIF_NIF_3 = "(worker.nif IS NULL OR worker.nif = ?)";
	private static final String _FINDER_COLUMN_EMAIL_EMAIL_1 = "worker.email IS NULL";
	private static final String _FINDER_COLUMN_EMAIL_EMAIL_2 = "worker.email = ?";
	private static final String _FINDER_COLUMN_EMAIL_EMAIL_3 = "(worker.email IS NULL OR worker.email = ?)";
	private static final String _FINDER_COLUMN_NAME_NAME_1 = "worker.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "worker.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(worker.name IS NULL OR worker.name = ?)";
	private static final String _FINDER_COLUMN_SURNAME_SURNAME_1 = "worker.surname IS NULL";
	private static final String _FINDER_COLUMN_SURNAME_SURNAME_2 = "worker.surname = ?";
	private static final String _FINDER_COLUMN_SURNAME_SURNAME_3 = "(worker.surname IS NULL OR worker.surname = ?)";
	private static final String _FINDER_COLUMN_G_NIF_GROUPID_2 = "worker.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_NIF_NIF_1 = "worker.nif IS NULL";
	private static final String _FINDER_COLUMN_G_NIF_NIF_2 = "worker.nif = ?";
	private static final String _FINDER_COLUMN_G_NIF_NIF_3 = "(worker.nif IS NULL OR worker.nif = ?)";
	private static final String _FINDER_COLUMN_G_EMAIL_GROUPID_2 = "worker.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_EMAIL_EMAIL_1 = "worker.email IS NULL";
	private static final String _FINDER_COLUMN_G_EMAIL_EMAIL_2 = "worker.email = ?";
	private static final String _FINDER_COLUMN_G_EMAIL_EMAIL_3 = "(worker.email IS NULL OR worker.email = ?)";
	private static final String _FINDER_COLUMN_G_NAME_GROUPID_2 = "worker.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_NAME_NAME_1 = "worker.name IS NULL";
	private static final String _FINDER_COLUMN_G_NAME_NAME_2 = "worker.name = ?";
	private static final String _FINDER_COLUMN_G_NAME_NAME_3 = "(worker.name IS NULL OR worker.name = ?)";
	private static final String _FINDER_COLUMN_G_SURNAME_GROUPID_2 = "worker.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_SURNAME_SURNAME_1 = "worker.surname IS NULL";
	private static final String _FINDER_COLUMN_G_SURNAME_SURNAME_2 = "worker.surname = ?";
	private static final String _FINDER_COLUMN_G_SURNAME_SURNAME_3 = "(worker.surname IS NULL OR worker.surname = ?)";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "worker.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "worker.userId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "worker.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Worker exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Worker exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(WorkerPersistenceImpl.class);
	private static Worker _nullWorker = new WorkerImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Worker> toCacheModel() {
				return _nullWorkerCacheModel;
			}
		};

	private static CacheModel<Worker> _nullWorkerCacheModel = new CacheModel<Worker>() {
			public Worker toEntityModel() {
				return _nullWorker;
			}
		};
}