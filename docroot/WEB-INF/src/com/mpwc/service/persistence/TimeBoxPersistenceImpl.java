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
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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

import com.mpwc.NoSuchTimeBoxException;

import com.mpwc.model.TimeBox;
import com.mpwc.model.impl.TimeBoxImpl;
import com.mpwc.model.impl.TimeBoxModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the time box service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author rsicart
 * @see TimeBoxPersistence
 * @see TimeBoxUtil
 * @generated
 */
public class TimeBoxPersistenceImpl extends BasePersistenceImpl<TimeBox>
	implements TimeBoxPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link TimeBoxUtil} to access the time box persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = TimeBoxImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the time box in the entity cache if it is enabled.
	 *
	 * @param timeBox the time box
	 */
	public void cacheResult(TimeBox timeBox) {
		EntityCacheUtil.putResult(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxImpl.class, timeBox.getPrimaryKey(), timeBox);

		timeBox.resetOriginalValues();
	}

	/**
	 * Caches the time boxs in the entity cache if it is enabled.
	 *
	 * @param timeBoxs the time boxs
	 */
	public void cacheResult(List<TimeBox> timeBoxs) {
		for (TimeBox timeBox : timeBoxs) {
			if (EntityCacheUtil.getResult(
						TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
						TimeBoxImpl.class, timeBox.getPrimaryKey()) == null) {
				cacheResult(timeBox);
			}
			else {
				timeBox.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all time boxs.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(TimeBoxImpl.class.getName());
		}

		EntityCacheUtil.clearCache(TimeBoxImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the time box.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TimeBox timeBox) {
		EntityCacheUtil.removeResult(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxImpl.class, timeBox.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<TimeBox> timeBoxs) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TimeBox timeBox : timeBoxs) {
			EntityCacheUtil.removeResult(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
				TimeBoxImpl.class, timeBox.getPrimaryKey());
		}
	}

	/**
	 * Creates a new time box with the primary key. Does not add the time box to the database.
	 *
	 * @param timeboxId the primary key for the new time box
	 * @return the new time box
	 */
	public TimeBox create(long timeboxId) {
		TimeBox timeBox = new TimeBoxImpl();

		timeBox.setNew(true);
		timeBox.setPrimaryKey(timeboxId);

		return timeBox;
	}

	/**
	 * Removes the time box with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param timeboxId the primary key of the time box
	 * @return the time box that was removed
	 * @throws com.mpwc.NoSuchTimeBoxException if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox remove(long timeboxId)
		throws NoSuchTimeBoxException, SystemException {
		return remove(Long.valueOf(timeboxId));
	}

	/**
	 * Removes the time box with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the time box
	 * @return the time box that was removed
	 * @throws com.mpwc.NoSuchTimeBoxException if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TimeBox remove(Serializable primaryKey)
		throws NoSuchTimeBoxException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TimeBox timeBox = (TimeBox)session.get(TimeBoxImpl.class, primaryKey);

			if (timeBox == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTimeBoxException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(timeBox);
		}
		catch (NoSuchTimeBoxException nsee) {
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
	protected TimeBox removeImpl(TimeBox timeBox) throws SystemException {
		timeBox = toUnwrappedModel(timeBox);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, timeBox);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(timeBox);

		return timeBox;
	}

	@Override
	public TimeBox updateImpl(com.mpwc.model.TimeBox timeBox, boolean merge)
		throws SystemException {
		timeBox = toUnwrappedModel(timeBox);

		boolean isNew = timeBox.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, timeBox, merge);

			timeBox.setNew(false);
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

		EntityCacheUtil.putResult(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxImpl.class, timeBox.getPrimaryKey(), timeBox);

		return timeBox;
	}

	protected TimeBox toUnwrappedModel(TimeBox timeBox) {
		if (timeBox instanceof TimeBoxImpl) {
			return timeBox;
		}

		TimeBoxImpl timeBoxImpl = new TimeBoxImpl();

		timeBoxImpl.setNew(timeBox.isNew());
		timeBoxImpl.setPrimaryKey(timeBox.getPrimaryKey());

		timeBoxImpl.setTimeboxId(timeBox.getTimeboxId());
		timeBoxImpl.setProjectId(timeBox.getProjectId());
		timeBoxImpl.setWorkerId(timeBox.getWorkerId());
		timeBoxImpl.setGroupId(timeBox.getGroupId());
		timeBoxImpl.setCompanyId(timeBox.getCompanyId());
		timeBoxImpl.setCreateDate(timeBox.getCreateDate());
		timeBoxImpl.setModifiedDate(timeBox.getModifiedDate());
		timeBoxImpl.setDedicationDate(timeBox.getDedicationDate());
		timeBoxImpl.setMinutes(timeBox.getMinutes());
		timeBoxImpl.setComments(timeBox.getComments());

		return timeBoxImpl;
	}

	/**
	 * Returns the time box with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the time box
	 * @return the time box
	 * @throws com.liferay.portal.NoSuchModelException if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TimeBox findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the time box with the primary key or throws a {@link com.mpwc.NoSuchTimeBoxException} if it could not be found.
	 *
	 * @param timeboxId the primary key of the time box
	 * @return the time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByPrimaryKey(long timeboxId)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByPrimaryKey(timeboxId);

		if (timeBox == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + timeboxId);
			}

			throw new NoSuchTimeBoxException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				timeboxId);
		}

		return timeBox;
	}

	/**
	 * Returns the time box with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the time box
	 * @return the time box, or <code>null</code> if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TimeBox fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the time box with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param timeboxId the primary key of the time box
	 * @return the time box, or <code>null</code> if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByPrimaryKey(long timeboxId) throws SystemException {
		TimeBox timeBox = (TimeBox)EntityCacheUtil.getResult(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
				TimeBoxImpl.class, timeboxId);

		if (timeBox == _nullTimeBox) {
			return null;
		}

		if (timeBox == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				timeBox = (TimeBox)session.get(TimeBoxImpl.class,
						Long.valueOf(timeboxId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (timeBox != null) {
					cacheResult(timeBox);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
						TimeBoxImpl.class, timeboxId, _nullTimeBox);
				}

				closeSession(session);
			}
		}

		return timeBox;
	}

	/**
	 * Returns all the time boxs.
	 *
	 * @return the time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the time boxs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @return the range of time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the time boxs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findAll(int start, int end,
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

		List<TimeBox> list = (List<TimeBox>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_TIMEBOX);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_TIMEBOX;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<TimeBox>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TimeBox>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the time boxs from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (TimeBox timeBox : findAll()) {
			remove(timeBox);
		}
	}

	/**
	 * Returns the number of time boxs.
	 *
	 * @return the number of time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_TIMEBOX);

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
	 * Initializes the time box persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.mpwc.model.TimeBox")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TimeBox>> listenersList = new ArrayList<ModelListener<TimeBox>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TimeBox>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(TimeBoxImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = ProjectPersistence.class)
	protected ProjectPersistence projectPersistence;
	@BeanReference(type = ProjectStatusPersistence.class)
	protected ProjectStatusPersistence projectStatusPersistence;
	@BeanReference(type = StatusPersistence.class)
	protected StatusPersistence statusPersistence;
	@BeanReference(type = TimeBoxPersistence.class)
	protected TimeBoxPersistence timeBoxPersistence;
	@BeanReference(type = WorkerPersistence.class)
	protected WorkerPersistence workerPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_TIMEBOX = "SELECT timeBox FROM TimeBox timeBox";
	private static final String _SQL_COUNT_TIMEBOX = "SELECT COUNT(timeBox) FROM TimeBox timeBox";
	private static final String _ORDER_BY_ENTITY_ALIAS = "timeBox.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No TimeBox exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(TimeBoxPersistenceImpl.class);
	private static TimeBox _nullTimeBox = new TimeBoxImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<TimeBox> toCacheModel() {
				return _nullTimeBoxCacheModel;
			}
		};

	private static CacheModel<TimeBox> _nullTimeBoxCacheModel = new CacheModel<TimeBox>() {
			public TimeBox toEntityModel() {
				return _nullTimeBox;
			}
		};
}