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
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
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

import com.mpwc.NoSuchTimeBoxException;

import com.mpwc.model.TimeBox;
import com.mpwc.model.impl.TimeBoxImpl;
import com.mpwc.model.impl.TimeBoxModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_P = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP",
			new String[] { Long.class.getName() },
			TimeBoxModelImpl.PROJECTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_P = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_W = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByW",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_W = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByW",
			new String[] { Long.class.getName() },
			TimeBoxModelImpl.WORKERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_W = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByW",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_P_W = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_W",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_W = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_W",
			new String[] { Long.class.getName(), Long.class.getName() },
			TimeBoxModelImpl.PROJECTID_COLUMN_BITMASK |
			TimeBoxModelImpl.WORKERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_P_W = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_W",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_P_W_DD = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_W_DD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_W_DD =
		new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_W_DD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			},
			TimeBoxModelImpl.PROJECTID_COLUMN_BITMASK |
			TimeBoxModelImpl.WORKERID_COLUMN_BITMASK |
			TimeBoxModelImpl.DEDICATIONDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_P_W_DD = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_W_DD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			TimeBoxModelImpl.GROUPID_COLUMN_BITMASK |
			TimeBoxModelImpl.PROJECTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_W = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_W",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_W = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, TimeBoxImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_W",
			new String[] { Long.class.getName(), Long.class.getName() },
			TimeBoxModelImpl.GROUPID_COLUMN_BITMASK |
			TimeBoxModelImpl.WORKERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_W = new FinderPath(TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			TimeBoxModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_W",
			new String[] { Long.class.getName(), Long.class.getName() });
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

		TimeBoxModelImpl timeBoxModelImpl = (TimeBoxModelImpl)timeBox;

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

		if (isNew || !TimeBoxModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((timeBoxModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(timeBoxModelImpl.getOriginalProjectId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P,
					args);

				args = new Object[] {
						Long.valueOf(timeBoxModelImpl.getProjectId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P,
					args);
			}

			if ((timeBoxModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_W.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(timeBoxModelImpl.getOriginalWorkerId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_W, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_W,
					args);

				args = new Object[] { Long.valueOf(timeBoxModelImpl.getWorkerId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_W, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_W,
					args);
			}

			if ((timeBoxModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_W.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(timeBoxModelImpl.getOriginalProjectId()),
						Long.valueOf(timeBoxModelImpl.getOriginalWorkerId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_W, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_W,
					args);

				args = new Object[] {
						Long.valueOf(timeBoxModelImpl.getProjectId()),
						Long.valueOf(timeBoxModelImpl.getWorkerId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_W, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_W,
					args);
			}

			if ((timeBoxModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_W_DD.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(timeBoxModelImpl.getOriginalProjectId()),
						Long.valueOf(timeBoxModelImpl.getOriginalWorkerId()),
						
						timeBoxModelImpl.getOriginalDedicationDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_W_DD, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_W_DD,
					args);

				args = new Object[] {
						Long.valueOf(timeBoxModelImpl.getProjectId()),
						Long.valueOf(timeBoxModelImpl.getWorkerId()),
						
						timeBoxModelImpl.getDedicationDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_W_DD, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_W_DD,
					args);
			}

			if ((timeBoxModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(timeBoxModelImpl.getOriginalGroupId()),
						Long.valueOf(timeBoxModelImpl.getOriginalProjectId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);

				args = new Object[] {
						Long.valueOf(timeBoxModelImpl.getGroupId()),
						Long.valueOf(timeBoxModelImpl.getProjectId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);
			}

			if ((timeBoxModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_W.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(timeBoxModelImpl.getOriginalGroupId()),
						Long.valueOf(timeBoxModelImpl.getOriginalWorkerId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_W, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_W,
					args);

				args = new Object[] {
						Long.valueOf(timeBoxModelImpl.getGroupId()),
						Long.valueOf(timeBoxModelImpl.getWorkerId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_W, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_W,
					args);
			}
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
	 * Returns all the time boxs where projectId = &#63;.
	 *
	 * @param projectId the project ID
	 * @return the matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByP(long projectId) throws SystemException {
		return findByP(projectId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the time boxs where projectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param projectId the project ID
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @return the range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByP(long projectId, int start, int end)
		throws SystemException {
		return findByP(projectId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the time boxs where projectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param projectId the project ID
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByP(long projectId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P;
			finderArgs = new Object[] { projectId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_P;
			finderArgs = new Object[] { projectId, start, end, orderByComparator };
		}

		List<TimeBox> list = (List<TimeBox>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (TimeBox timeBox : list) {
				if ((projectId != timeBox.getProjectId())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_P_PROJECTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(projectId);

				list = (List<TimeBox>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first time box in the ordered set where projectId = &#63;.
	 *
	 * @param projectId the project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByP_First(long projectId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByP_First(projectId, orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("projectId=");
		msg.append(projectId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the first time box in the ordered set where projectId = &#63;.
	 *
	 * @param projectId the project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByP_First(long projectId,
		OrderByComparator orderByComparator) throws SystemException {
		List<TimeBox> list = findByP(projectId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last time box in the ordered set where projectId = &#63;.
	 *
	 * @param projectId the project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByP_Last(long projectId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByP_Last(projectId, orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("projectId=");
		msg.append(projectId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the last time box in the ordered set where projectId = &#63;.
	 *
	 * @param projectId the project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByP_Last(long projectId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByP(projectId);

		List<TimeBox> list = findByP(projectId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the time boxs before and after the current time box in the ordered set where projectId = &#63;.
	 *
	 * @param timeboxId the primary key of the current time box
	 * @param projectId the project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox[] findByP_PrevAndNext(long timeboxId, long projectId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = findByPrimaryKey(timeboxId);

		Session session = null;

		try {
			session = openSession();

			TimeBox[] array = new TimeBoxImpl[3];

			array[0] = getByP_PrevAndNext(session, timeBox, projectId,
					orderByComparator, true);

			array[1] = timeBox;

			array[2] = getByP_PrevAndNext(session, timeBox, projectId,
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

	protected TimeBox getByP_PrevAndNext(Session session, TimeBox timeBox,
		long projectId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TIMEBOX_WHERE);

		query.append(_FINDER_COLUMN_P_PROJECTID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(projectId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(timeBox);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TimeBox> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the time boxs where workerId = &#63;.
	 *
	 * @param workerId the worker ID
	 * @return the matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByW(long workerId) throws SystemException {
		return findByW(workerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the time boxs where workerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param workerId the worker ID
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @return the range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByW(long workerId, int start, int end)
		throws SystemException {
		return findByW(workerId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the time boxs where workerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param workerId the worker ID
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByW(long workerId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_W;
			finderArgs = new Object[] { workerId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_W;
			finderArgs = new Object[] { workerId, start, end, orderByComparator };
		}

		List<TimeBox> list = (List<TimeBox>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (TimeBox timeBox : list) {
				if ((workerId != timeBox.getWorkerId())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_W_WORKERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(workerId);

				list = (List<TimeBox>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first time box in the ordered set where workerId = &#63;.
	 *
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByW_First(long workerId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByW_First(workerId, orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("workerId=");
		msg.append(workerId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the first time box in the ordered set where workerId = &#63;.
	 *
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByW_First(long workerId,
		OrderByComparator orderByComparator) throws SystemException {
		List<TimeBox> list = findByW(workerId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last time box in the ordered set where workerId = &#63;.
	 *
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByW_Last(long workerId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByW_Last(workerId, orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("workerId=");
		msg.append(workerId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the last time box in the ordered set where workerId = &#63;.
	 *
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByW_Last(long workerId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByW(workerId);

		List<TimeBox> list = findByW(workerId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the time boxs before and after the current time box in the ordered set where workerId = &#63;.
	 *
	 * @param timeboxId the primary key of the current time box
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox[] findByW_PrevAndNext(long timeboxId, long workerId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = findByPrimaryKey(timeboxId);

		Session session = null;

		try {
			session = openSession();

			TimeBox[] array = new TimeBoxImpl[3];

			array[0] = getByW_PrevAndNext(session, timeBox, workerId,
					orderByComparator, true);

			array[1] = timeBox;

			array[2] = getByW_PrevAndNext(session, timeBox, workerId,
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

	protected TimeBox getByW_PrevAndNext(Session session, TimeBox timeBox,
		long workerId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TIMEBOX_WHERE);

		query.append(_FINDER_COLUMN_W_WORKERID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(workerId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(timeBox);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TimeBox> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the time boxs where projectId = &#63; and workerId = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @return the matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByP_W(long projectId, long workerId)
		throws SystemException {
		return findByP_W(projectId, workerId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the time boxs where projectId = &#63; and workerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @return the range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByP_W(long projectId, long workerId, int start,
		int end) throws SystemException {
		return findByP_W(projectId, workerId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the time boxs where projectId = &#63; and workerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByP_W(long projectId, long workerId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_W;
			finderArgs = new Object[] { projectId, workerId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_P_W;
			finderArgs = new Object[] {
					projectId, workerId,
					
					start, end, orderByComparator
				};
		}

		List<TimeBox> list = (List<TimeBox>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (TimeBox timeBox : list) {
				if ((projectId != timeBox.getProjectId()) ||
						(workerId != timeBox.getWorkerId())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_P_W_PROJECTID_2);

			query.append(_FINDER_COLUMN_P_W_WORKERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(projectId);

				qPos.add(workerId);

				list = (List<TimeBox>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first time box in the ordered set where projectId = &#63; and workerId = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByP_W_First(long projectId, long workerId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByP_W_First(projectId, workerId,
				orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("projectId=");
		msg.append(projectId);

		msg.append(", workerId=");
		msg.append(workerId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the first time box in the ordered set where projectId = &#63; and workerId = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByP_W_First(long projectId, long workerId,
		OrderByComparator orderByComparator) throws SystemException {
		List<TimeBox> list = findByP_W(projectId, workerId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last time box in the ordered set where projectId = &#63; and workerId = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByP_W_Last(long projectId, long workerId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByP_W_Last(projectId, workerId, orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("projectId=");
		msg.append(projectId);

		msg.append(", workerId=");
		msg.append(workerId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the last time box in the ordered set where projectId = &#63; and workerId = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByP_W_Last(long projectId, long workerId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByP_W(projectId, workerId);

		List<TimeBox> list = findByP_W(projectId, workerId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the time boxs before and after the current time box in the ordered set where projectId = &#63; and workerId = &#63;.
	 *
	 * @param timeboxId the primary key of the current time box
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox[] findByP_W_PrevAndNext(long timeboxId, long projectId,
		long workerId, OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = findByPrimaryKey(timeboxId);

		Session session = null;

		try {
			session = openSession();

			TimeBox[] array = new TimeBoxImpl[3];

			array[0] = getByP_W_PrevAndNext(session, timeBox, projectId,
					workerId, orderByComparator, true);

			array[1] = timeBox;

			array[2] = getByP_W_PrevAndNext(session, timeBox, projectId,
					workerId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected TimeBox getByP_W_PrevAndNext(Session session, TimeBox timeBox,
		long projectId, long workerId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TIMEBOX_WHERE);

		query.append(_FINDER_COLUMN_P_W_PROJECTID_2);

		query.append(_FINDER_COLUMN_P_W_WORKERID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(projectId);

		qPos.add(workerId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(timeBox);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TimeBox> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the time boxs where projectId = &#63; and workerId = &#63; and dedicationDate = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param dedicationDate the dedication date
	 * @return the matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByP_W_DD(long projectId, long workerId,
		Date dedicationDate) throws SystemException {
		return findByP_W_DD(projectId, workerId, dedicationDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the time boxs where projectId = &#63; and workerId = &#63; and dedicationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param dedicationDate the dedication date
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @return the range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByP_W_DD(long projectId, long workerId,
		Date dedicationDate, int start, int end) throws SystemException {
		return findByP_W_DD(projectId, workerId, dedicationDate, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the time boxs where projectId = &#63; and workerId = &#63; and dedicationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param dedicationDate the dedication date
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByP_W_DD(long projectId, long workerId,
		Date dedicationDate, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_W_DD;
			finderArgs = new Object[] { projectId, workerId, dedicationDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_P_W_DD;
			finderArgs = new Object[] {
					projectId, workerId, dedicationDate,
					
					start, end, orderByComparator
				};
		}

		List<TimeBox> list = (List<TimeBox>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (TimeBox timeBox : list) {
				if ((projectId != timeBox.getProjectId()) ||
						(workerId != timeBox.getWorkerId()) ||
						!Validator.equals(dedicationDate,
							timeBox.getDedicationDate())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_P_W_DD_PROJECTID_2);

			query.append(_FINDER_COLUMN_P_W_DD_WORKERID_2);

			if (dedicationDate == null) {
				query.append(_FINDER_COLUMN_P_W_DD_DEDICATIONDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_P_W_DD_DEDICATIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(projectId);

				qPos.add(workerId);

				if (dedicationDate != null) {
					qPos.add(CalendarUtil.getTimestamp(dedicationDate));
				}

				list = (List<TimeBox>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first time box in the ordered set where projectId = &#63; and workerId = &#63; and dedicationDate = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param dedicationDate the dedication date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByP_W_DD_First(long projectId, long workerId,
		Date dedicationDate, OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByP_W_DD_First(projectId, workerId,
				dedicationDate, orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("projectId=");
		msg.append(projectId);

		msg.append(", workerId=");
		msg.append(workerId);

		msg.append(", dedicationDate=");
		msg.append(dedicationDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the first time box in the ordered set where projectId = &#63; and workerId = &#63; and dedicationDate = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param dedicationDate the dedication date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByP_W_DD_First(long projectId, long workerId,
		Date dedicationDate, OrderByComparator orderByComparator)
		throws SystemException {
		List<TimeBox> list = findByP_W_DD(projectId, workerId, dedicationDate,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last time box in the ordered set where projectId = &#63; and workerId = &#63; and dedicationDate = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param dedicationDate the dedication date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByP_W_DD_Last(long projectId, long workerId,
		Date dedicationDate, OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByP_W_DD_Last(projectId, workerId,
				dedicationDate, orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("projectId=");
		msg.append(projectId);

		msg.append(", workerId=");
		msg.append(workerId);

		msg.append(", dedicationDate=");
		msg.append(dedicationDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the last time box in the ordered set where projectId = &#63; and workerId = &#63; and dedicationDate = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param dedicationDate the dedication date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByP_W_DD_Last(long projectId, long workerId,
		Date dedicationDate, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByP_W_DD(projectId, workerId, dedicationDate);

		List<TimeBox> list = findByP_W_DD(projectId, workerId, dedicationDate,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the time boxs before and after the current time box in the ordered set where projectId = &#63; and workerId = &#63; and dedicationDate = &#63;.
	 *
	 * @param timeboxId the primary key of the current time box
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param dedicationDate the dedication date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox[] findByP_W_DD_PrevAndNext(long timeboxId, long projectId,
		long workerId, Date dedicationDate, OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = findByPrimaryKey(timeboxId);

		Session session = null;

		try {
			session = openSession();

			TimeBox[] array = new TimeBoxImpl[3];

			array[0] = getByP_W_DD_PrevAndNext(session, timeBox, projectId,
					workerId, dedicationDate, orderByComparator, true);

			array[1] = timeBox;

			array[2] = getByP_W_DD_PrevAndNext(session, timeBox, projectId,
					workerId, dedicationDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected TimeBox getByP_W_DD_PrevAndNext(Session session, TimeBox timeBox,
		long projectId, long workerId, Date dedicationDate,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TIMEBOX_WHERE);

		query.append(_FINDER_COLUMN_P_W_DD_PROJECTID_2);

		query.append(_FINDER_COLUMN_P_W_DD_WORKERID_2);

		if (dedicationDate == null) {
			query.append(_FINDER_COLUMN_P_W_DD_DEDICATIONDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_P_W_DD_DEDICATIONDATE_2);
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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(projectId);

		qPos.add(workerId);

		if (dedicationDate != null) {
			qPos.add(CalendarUtil.getTimestamp(dedicationDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(timeBox);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TimeBox> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the time boxs where groupId = &#63; and projectId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param projectId the project ID
	 * @return the matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByG_P(long groupId, long projectId)
		throws SystemException {
		return findByG_P(groupId, projectId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the time boxs where groupId = &#63; and projectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param projectId the project ID
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @return the range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByG_P(long groupId, long projectId, int start,
		int end) throws SystemException {
		return findByG_P(groupId, projectId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the time boxs where groupId = &#63; and projectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param projectId the project ID
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByG_P(long groupId, long projectId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] { groupId, projectId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] {
					groupId, projectId,
					
					start, end, orderByComparator
				};
		}

		List<TimeBox> list = (List<TimeBox>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (TimeBox timeBox : list) {
				if ((groupId != timeBox.getGroupId()) ||
						(projectId != timeBox.getProjectId())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PROJECTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(projectId);

				list = (List<TimeBox>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first time box in the ordered set where groupId = &#63; and projectId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param projectId the project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByG_P_First(long groupId, long projectId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByG_P_First(groupId, projectId, orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", projectId=");
		msg.append(projectId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the first time box in the ordered set where groupId = &#63; and projectId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param projectId the project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByG_P_First(long groupId, long projectId,
		OrderByComparator orderByComparator) throws SystemException {
		List<TimeBox> list = findByG_P(groupId, projectId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last time box in the ordered set where groupId = &#63; and projectId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param projectId the project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByG_P_Last(long groupId, long projectId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByG_P_Last(groupId, projectId, orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", projectId=");
		msg.append(projectId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the last time box in the ordered set where groupId = &#63; and projectId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param projectId the project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByG_P_Last(long groupId, long projectId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_P(groupId, projectId);

		List<TimeBox> list = findByG_P(groupId, projectId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the time boxs before and after the current time box in the ordered set where groupId = &#63; and projectId = &#63;.
	 *
	 * @param timeboxId the primary key of the current time box
	 * @param groupId the group ID
	 * @param projectId the project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox[] findByG_P_PrevAndNext(long timeboxId, long groupId,
		long projectId, OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = findByPrimaryKey(timeboxId);

		Session session = null;

		try {
			session = openSession();

			TimeBox[] array = new TimeBoxImpl[3];

			array[0] = getByG_P_PrevAndNext(session, timeBox, groupId,
					projectId, orderByComparator, true);

			array[1] = timeBox;

			array[2] = getByG_P_PrevAndNext(session, timeBox, groupId,
					projectId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected TimeBox getByG_P_PrevAndNext(Session session, TimeBox timeBox,
		long groupId, long projectId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TIMEBOX_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PROJECTID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(projectId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(timeBox);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TimeBox> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the time boxs where groupId = &#63; and workerId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param workerId the worker ID
	 * @return the matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByG_W(long groupId, long workerId)
		throws SystemException {
		return findByG_W(groupId, workerId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the time boxs where groupId = &#63; and workerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param workerId the worker ID
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @return the range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByG_W(long groupId, long workerId, int start,
		int end) throws SystemException {
		return findByG_W(groupId, workerId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the time boxs where groupId = &#63; and workerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param workerId the worker ID
	 * @param start the lower bound of the range of time boxs
	 * @param end the upper bound of the range of time boxs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public List<TimeBox> findByG_W(long groupId, long workerId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_W;
			finderArgs = new Object[] { groupId, workerId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_W;
			finderArgs = new Object[] {
					groupId, workerId,
					
					start, end, orderByComparator
				};
		}

		List<TimeBox> list = (List<TimeBox>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (TimeBox timeBox : list) {
				if ((groupId != timeBox.getGroupId()) ||
						(workerId != timeBox.getWorkerId())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_G_W_GROUPID_2);

			query.append(_FINDER_COLUMN_G_W_WORKERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(workerId);

				list = (List<TimeBox>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first time box in the ordered set where groupId = &#63; and workerId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByG_W_First(long groupId, long workerId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByG_W_First(groupId, workerId, orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", workerId=");
		msg.append(workerId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the first time box in the ordered set where groupId = &#63; and workerId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByG_W_First(long groupId, long workerId,
		OrderByComparator orderByComparator) throws SystemException {
		List<TimeBox> list = findByG_W(groupId, workerId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last time box in the ordered set where groupId = &#63; and workerId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox findByG_W_Last(long groupId, long workerId,
		OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = fetchByG_W_Last(groupId, workerId, orderByComparator);

		if (timeBox != null) {
			return timeBox;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", workerId=");
		msg.append(workerId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTimeBoxException(msg.toString());
	}

	/**
	 * Returns the last time box in the ordered set where groupId = &#63; and workerId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching time box, or <code>null</code> if a matching time box could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox fetchByG_W_Last(long groupId, long workerId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_W(groupId, workerId);

		List<TimeBox> list = findByG_W(groupId, workerId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the time boxs before and after the current time box in the ordered set where groupId = &#63; and workerId = &#63;.
	 *
	 * @param timeboxId the primary key of the current time box
	 * @param groupId the group ID
	 * @param workerId the worker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next time box
	 * @throws com.mpwc.NoSuchTimeBoxException if a time box with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TimeBox[] findByG_W_PrevAndNext(long timeboxId, long groupId,
		long workerId, OrderByComparator orderByComparator)
		throws NoSuchTimeBoxException, SystemException {
		TimeBox timeBox = findByPrimaryKey(timeboxId);

		Session session = null;

		try {
			session = openSession();

			TimeBox[] array = new TimeBoxImpl[3];

			array[0] = getByG_W_PrevAndNext(session, timeBox, groupId,
					workerId, orderByComparator, true);

			array[1] = timeBox;

			array[2] = getByG_W_PrevAndNext(session, timeBox, groupId,
					workerId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected TimeBox getByG_W_PrevAndNext(Session session, TimeBox timeBox,
		long groupId, long workerId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TIMEBOX_WHERE);

		query.append(_FINDER_COLUMN_G_W_GROUPID_2);

		query.append(_FINDER_COLUMN_G_W_WORKERID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(workerId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(timeBox);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TimeBox> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
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
	 * Removes all the time boxs where projectId = &#63; from the database.
	 *
	 * @param projectId the project ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByP(long projectId) throws SystemException {
		for (TimeBox timeBox : findByP(projectId)) {
			remove(timeBox);
		}
	}

	/**
	 * Removes all the time boxs where workerId = &#63; from the database.
	 *
	 * @param workerId the worker ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByW(long workerId) throws SystemException {
		for (TimeBox timeBox : findByW(workerId)) {
			remove(timeBox);
		}
	}

	/**
	 * Removes all the time boxs where projectId = &#63; and workerId = &#63; from the database.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByP_W(long projectId, long workerId)
		throws SystemException {
		for (TimeBox timeBox : findByP_W(projectId, workerId)) {
			remove(timeBox);
		}
	}

	/**
	 * Removes all the time boxs where projectId = &#63; and workerId = &#63; and dedicationDate = &#63; from the database.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param dedicationDate the dedication date
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByP_W_DD(long projectId, long workerId,
		Date dedicationDate) throws SystemException {
		for (TimeBox timeBox : findByP_W_DD(projectId, workerId, dedicationDate)) {
			remove(timeBox);
		}
	}

	/**
	 * Removes all the time boxs where groupId = &#63; and projectId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param projectId the project ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P(long groupId, long projectId)
		throws SystemException {
		for (TimeBox timeBox : findByG_P(groupId, projectId)) {
			remove(timeBox);
		}
	}

	/**
	 * Removes all the time boxs where groupId = &#63; and workerId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param workerId the worker ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_W(long groupId, long workerId)
		throws SystemException {
		for (TimeBox timeBox : findByG_W(groupId, workerId)) {
			remove(timeBox);
		}
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
	 * Returns the number of time boxs where projectId = &#63;.
	 *
	 * @param projectId the project ID
	 * @return the number of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP(long projectId) throws SystemException {
		Object[] finderArgs = new Object[] { projectId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_P_PROJECTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(projectId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of time boxs where workerId = &#63;.
	 *
	 * @param workerId the worker ID
	 * @return the number of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByW(long workerId) throws SystemException {
		Object[] finderArgs = new Object[] { workerId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_W,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_W_WORKERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(workerId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_W, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of time boxs where projectId = &#63; and workerId = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @return the number of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP_W(long projectId, long workerId)
		throws SystemException {
		Object[] finderArgs = new Object[] { projectId, workerId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_W,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_P_W_PROJECTID_2);

			query.append(_FINDER_COLUMN_P_W_WORKERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(projectId);

				qPos.add(workerId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_W, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of time boxs where projectId = &#63; and workerId = &#63; and dedicationDate = &#63;.
	 *
	 * @param projectId the project ID
	 * @param workerId the worker ID
	 * @param dedicationDate the dedication date
	 * @return the number of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP_W_DD(long projectId, long workerId, Date dedicationDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { projectId, workerId, dedicationDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_W_DD,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_P_W_DD_PROJECTID_2);

			query.append(_FINDER_COLUMN_P_W_DD_WORKERID_2);

			if (dedicationDate == null) {
				query.append(_FINDER_COLUMN_P_W_DD_DEDICATIONDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_P_W_DD_DEDICATIONDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(projectId);

				qPos.add(workerId);

				if (dedicationDate != null) {
					qPos.add(CalendarUtil.getTimestamp(dedicationDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_W_DD,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of time boxs where groupId = &#63; and projectId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param projectId the project ID
	 * @return the number of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P(long groupId, long projectId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, projectId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PROJECTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(projectId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of time boxs where groupId = &#63; and workerId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param workerId the worker ID
	 * @return the number of matching time boxs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_W(long groupId, long workerId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, workerId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_W,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_TIMEBOX_WHERE);

			query.append(_FINDER_COLUMN_G_W_GROUPID_2);

			query.append(_FINDER_COLUMN_G_W_WORKERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(workerId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_W, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
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
	private static final String _SQL_SELECT_TIMEBOX_WHERE = "SELECT timeBox FROM TimeBox timeBox WHERE ";
	private static final String _SQL_COUNT_TIMEBOX = "SELECT COUNT(timeBox) FROM TimeBox timeBox";
	private static final String _SQL_COUNT_TIMEBOX_WHERE = "SELECT COUNT(timeBox) FROM TimeBox timeBox WHERE ";
	private static final String _FINDER_COLUMN_P_PROJECTID_2 = "timeBox.projectId = ?";
	private static final String _FINDER_COLUMN_W_WORKERID_2 = "timeBox.workerId = ?";
	private static final String _FINDER_COLUMN_P_W_PROJECTID_2 = "timeBox.projectId = ? AND ";
	private static final String _FINDER_COLUMN_P_W_WORKERID_2 = "timeBox.workerId = ?";
	private static final String _FINDER_COLUMN_P_W_DD_PROJECTID_2 = "timeBox.projectId = ? AND ";
	private static final String _FINDER_COLUMN_P_W_DD_WORKERID_2 = "timeBox.workerId = ? AND ";
	private static final String _FINDER_COLUMN_P_W_DD_DEDICATIONDATE_1 = "timeBox.dedicationDate IS NULL";
	private static final String _FINDER_COLUMN_P_W_DD_DEDICATIONDATE_2 = "timeBox.dedicationDate = ?";
	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "timeBox.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PROJECTID_2 = "timeBox.projectId = ?";
	private static final String _FINDER_COLUMN_G_W_GROUPID_2 = "timeBox.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_W_WORKERID_2 = "timeBox.workerId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "timeBox.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No TimeBox exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No TimeBox exists with the key {";
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