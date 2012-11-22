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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.mpwc.NoSuchStatusException;

import com.mpwc.model.Status;
import com.mpwc.model.impl.StatusImpl;
import com.mpwc.model.impl.StatusModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the status service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author rsicart
 * @see StatusPersistence
 * @see StatusUtil
 * @generated
 */
public class StatusPersistenceImpl extends BasePersistenceImpl<Status>
	implements StatusPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link StatusUtil} to access the status persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = StatusImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, StatusImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, StatusImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the status in the entity cache if it is enabled.
	 *
	 * @param status the status
	 */
	public void cacheResult(Status status) {
		EntityCacheUtil.putResult(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusImpl.class, status.getPrimaryKey(), status);

		status.resetOriginalValues();
	}

	/**
	 * Caches the statuses in the entity cache if it is enabled.
	 *
	 * @param statuses the statuses
	 */
	public void cacheResult(List<Status> statuses) {
		for (Status status : statuses) {
			if (EntityCacheUtil.getResult(
						StatusModelImpl.ENTITY_CACHE_ENABLED, StatusImpl.class,
						status.getPrimaryKey()) == null) {
				cacheResult(status);
			}
			else {
				status.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all statuses.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(StatusImpl.class.getName());
		}

		EntityCacheUtil.clearCache(StatusImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the status.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Status status) {
		EntityCacheUtil.removeResult(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusImpl.class, status.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Status> statuses) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Status status : statuses) {
			EntityCacheUtil.removeResult(StatusModelImpl.ENTITY_CACHE_ENABLED,
				StatusImpl.class, status.getPrimaryKey());
		}
	}

	/**
	 * Creates a new status with the primary key. Does not add the status to the database.
	 *
	 * @param statusId the primary key for the new status
	 * @return the new status
	 */
	public Status create(long statusId) {
		Status status = new StatusImpl();

		status.setNew(true);
		status.setPrimaryKey(statusId);

		return status;
	}

	/**
	 * Removes the status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statusId the primary key of the status
	 * @return the status that was removed
	 * @throws com.mpwc.NoSuchStatusException if a status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Status remove(long statusId)
		throws NoSuchStatusException, SystemException {
		return remove(Long.valueOf(statusId));
	}

	/**
	 * Removes the status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the status
	 * @return the status that was removed
	 * @throws com.mpwc.NoSuchStatusException if a status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Status remove(Serializable primaryKey)
		throws NoSuchStatusException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Status status = (Status)session.get(StatusImpl.class, primaryKey);

			if (status == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStatusException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(status);
		}
		catch (NoSuchStatusException nsee) {
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
	protected Status removeImpl(Status status) throws SystemException {
		status = toUnwrappedModel(status);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, status);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(status);

		return status;
	}

	@Override
	public Status updateImpl(com.mpwc.model.Status status, boolean merge)
		throws SystemException {
		status = toUnwrappedModel(status);

		boolean isNew = status.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, status, merge);

			status.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusImpl.class, status.getPrimaryKey(), status);

		return status;
	}

	protected Status toUnwrappedModel(Status status) {
		if (status instanceof StatusImpl) {
			return status;
		}

		StatusImpl statusImpl = new StatusImpl();

		statusImpl.setNew(status.isNew());
		statusImpl.setPrimaryKey(status.getPrimaryKey());

		statusImpl.setStatusId(status.getStatusId());
		statusImpl.setDesc_en_US(status.getDesc_en_US());
		statusImpl.setDesc_es_ES(status.getDesc_es_ES());
		statusImpl.setDesc_ca_ES(status.getDesc_ca_ES());

		return statusImpl;
	}

	/**
	 * Returns the status with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the status
	 * @return the status
	 * @throws com.liferay.portal.NoSuchModelException if a status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Status findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the status with the primary key or throws a {@link com.mpwc.NoSuchStatusException} if it could not be found.
	 *
	 * @param statusId the primary key of the status
	 * @return the status
	 * @throws com.mpwc.NoSuchStatusException if a status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Status findByPrimaryKey(long statusId)
		throws NoSuchStatusException, SystemException {
		Status status = fetchByPrimaryKey(statusId);

		if (status == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + statusId);
			}

			throw new NoSuchStatusException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				statusId);
		}

		return status;
	}

	/**
	 * Returns the status with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the status
	 * @return the status, or <code>null</code> if a status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Status fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the status with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param statusId the primary key of the status
	 * @return the status, or <code>null</code> if a status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Status fetchByPrimaryKey(long statusId) throws SystemException {
		Status status = (Status)EntityCacheUtil.getResult(StatusModelImpl.ENTITY_CACHE_ENABLED,
				StatusImpl.class, statusId);

		if (status == _nullStatus) {
			return null;
		}

		if (status == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				status = (Status)session.get(StatusImpl.class,
						Long.valueOf(statusId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (status != null) {
					cacheResult(status);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(StatusModelImpl.ENTITY_CACHE_ENABLED,
						StatusImpl.class, statusId, _nullStatus);
				}

				closeSession(session);
			}
		}

		return status;
	}

	/**
	 * Returns all the statuses.
	 *
	 * @return the statuses
	 * @throws SystemException if a system exception occurred
	 */
	public List<Status> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @return the range of statuses
	 * @throws SystemException if a system exception occurred
	 */
	public List<Status> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of statuses
	 * @throws SystemException if a system exception occurred
	 */
	public List<Status> findAll(int start, int end,
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

		List<Status> list = (List<Status>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_STATUS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_STATUS;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Status>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Status>)QueryUtil.list(q, getDialect(), start,
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
	 * Removes all the statuses from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Status status : findAll()) {
			remove(status);
		}
	}

	/**
	 * Returns the number of statuses.
	 *
	 * @return the number of statuses
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_STATUS);

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
	 * Returns all the workers associated with the status.
	 *
	 * @param pk the primary key of the status
	 * @return the workers associated with the status
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Worker> getWorkers(long pk)
		throws SystemException {
		return getWorkers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the workers associated with the status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the status
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @return the range of workers associated with the status
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Worker> getWorkers(long pk, int start, int end)
		throws SystemException {
		return getWorkers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_WORKERS = new FinderPath(com.mpwc.model.impl.WorkerModelImpl.ENTITY_CACHE_ENABLED,
			com.mpwc.model.impl.WorkerModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.WorkerImpl.class,
			com.mpwc.service.persistence.WorkerPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"getWorkers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_WORKERS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the workers associated with the status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the status
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of workers associated with the status
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Worker> getWorkers(long pk, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.mpwc.model.Worker> list = (List<com.mpwc.model.Worker>)FinderCacheUtil.getResult(FINDER_PATH_GET_WORKERS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETWORKERS.concat(ORDER_BY_CLAUSE)
										 .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETWORKERS.concat(com.mpwc.model.impl.WorkerModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("tbl_mpwc_workers",
					com.mpwc.model.impl.WorkerImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.mpwc.model.Worker>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_WORKERS,
						finderArgs);
				}
				else {
					workerPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_WORKERS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_WORKERS_SIZE = new FinderPath(com.mpwc.model.impl.WorkerModelImpl.ENTITY_CACHE_ENABLED,
			com.mpwc.model.impl.WorkerModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.WorkerImpl.class,
			com.mpwc.service.persistence.WorkerPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"getWorkersSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_WORKERS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of workers associated with the status.
	 *
	 * @param pk the primary key of the status
	 * @return the number of workers associated with the status
	 * @throws SystemException if a system exception occurred
	 */
	public int getWorkersSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_WORKERS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETWORKERSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_WORKERS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_WORKER = new FinderPath(com.mpwc.model.impl.WorkerModelImpl.ENTITY_CACHE_ENABLED,
			com.mpwc.model.impl.WorkerModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.WorkerImpl.class,
			com.mpwc.service.persistence.WorkerPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"containsWorker",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the worker is associated with the status.
	 *
	 * @param pk the primary key of the status
	 * @param workerPK the primary key of the worker
	 * @return <code>true</code> if the worker is associated with the status; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsWorker(long pk, long workerPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, workerPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_WORKER,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsWorker.contains(pk, workerPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_WORKER,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the status has any workers associated with it.
	 *
	 * @param pk the primary key of the status to check for associations with workers
	 * @return <code>true</code> if the status has any workers associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsWorkers(long pk) throws SystemException {
		if (getWorkersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Initializes the status persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.mpwc.model.Status")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Status>> listenersList = new ArrayList<ModelListener<Status>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Status>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsWorker = new ContainsWorker();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(StatusImpl.class.getName());
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
	protected ContainsWorker containsWorker;

	protected class ContainsWorker {
		protected ContainsWorker() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSWORKER,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long statusId, long workerId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(statusId), new Long(workerId)
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

	private static final String _SQL_SELECT_STATUS = "SELECT status FROM Status status";
	private static final String _SQL_COUNT_STATUS = "SELECT COUNT(status) FROM Status status";
	private static final String _SQL_GETWORKERS = "SELECT {tbl_mpwc_workers.*} FROM tbl_mpwc_workers INNER JOIN tbl_mpwc_workerstatus ON (tbl_mpwc_workerstatus.statusId = tbl_mpwc_workers.statusId) WHERE (tbl_mpwc_workerstatus.statusId = ?)";
	private static final String _SQL_GETWORKERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM tbl_mpwc_workers WHERE statusId = ?";
	private static final String _SQL_CONTAINSWORKER = "SELECT COUNT(*) AS COUNT_VALUE FROM tbl_mpwc_workers WHERE statusId = ? AND workerId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "status.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Status exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(StatusPersistenceImpl.class);
	private static Status _nullStatus = new StatusImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Status> toCacheModel() {
				return _nullStatusCacheModel;
			}
		};

	private static CacheModel<Status> _nullStatusCacheModel = new CacheModel<Status>() {
			public Status toEntityModel() {
				return _nullStatus;
			}
		};
}