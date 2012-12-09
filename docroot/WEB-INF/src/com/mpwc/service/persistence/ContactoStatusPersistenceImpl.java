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

import com.mpwc.NoSuchContactoStatusException;

import com.mpwc.model.ContactoStatus;
import com.mpwc.model.impl.ContactoStatusImpl;
import com.mpwc.model.impl.ContactoStatusModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the contacto status service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author rsicart
 * @see ContactoStatusPersistence
 * @see ContactoStatusUtil
 * @generated
 */
public class ContactoStatusPersistenceImpl extends BasePersistenceImpl<ContactoStatus>
	implements ContactoStatusPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ContactoStatusUtil} to access the contacto status persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ContactoStatusImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ContactoStatusModelImpl.ENTITY_CACHE_ENABLED,
			ContactoStatusModelImpl.FINDER_CACHE_ENABLED,
			ContactoStatusImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ContactoStatusModelImpl.ENTITY_CACHE_ENABLED,
			ContactoStatusModelImpl.FINDER_CACHE_ENABLED,
			ContactoStatusImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ContactoStatusModelImpl.ENTITY_CACHE_ENABLED,
			ContactoStatusModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the contacto status in the entity cache if it is enabled.
	 *
	 * @param contactoStatus the contacto status
	 */
	public void cacheResult(ContactoStatus contactoStatus) {
		EntityCacheUtil.putResult(ContactoStatusModelImpl.ENTITY_CACHE_ENABLED,
			ContactoStatusImpl.class, contactoStatus.getPrimaryKey(),
			contactoStatus);

		contactoStatus.resetOriginalValues();
	}

	/**
	 * Caches the contacto statuses in the entity cache if it is enabled.
	 *
	 * @param contactoStatuses the contacto statuses
	 */
	public void cacheResult(List<ContactoStatus> contactoStatuses) {
		for (ContactoStatus contactoStatus : contactoStatuses) {
			if (EntityCacheUtil.getResult(
						ContactoStatusModelImpl.ENTITY_CACHE_ENABLED,
						ContactoStatusImpl.class, contactoStatus.getPrimaryKey()) == null) {
				cacheResult(contactoStatus);
			}
			else {
				contactoStatus.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all contacto statuses.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ContactoStatusImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ContactoStatusImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the contacto status.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ContactoStatus contactoStatus) {
		EntityCacheUtil.removeResult(ContactoStatusModelImpl.ENTITY_CACHE_ENABLED,
			ContactoStatusImpl.class, contactoStatus.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ContactoStatus> contactoStatuses) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ContactoStatus contactoStatus : contactoStatuses) {
			EntityCacheUtil.removeResult(ContactoStatusModelImpl.ENTITY_CACHE_ENABLED,
				ContactoStatusImpl.class, contactoStatus.getPrimaryKey());
		}
	}

	/**
	 * Creates a new contacto status with the primary key. Does not add the contacto status to the database.
	 *
	 * @param contactStatusId the primary key for the new contacto status
	 * @return the new contacto status
	 */
	public ContactoStatus create(long contactStatusId) {
		ContactoStatus contactoStatus = new ContactoStatusImpl();

		contactoStatus.setNew(true);
		contactoStatus.setPrimaryKey(contactStatusId);

		return contactoStatus;
	}

	/**
	 * Removes the contacto status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactStatusId the primary key of the contacto status
	 * @return the contacto status that was removed
	 * @throws com.mpwc.NoSuchContactoStatusException if a contacto status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ContactoStatus remove(long contactStatusId)
		throws NoSuchContactoStatusException, SystemException {
		return remove(Long.valueOf(contactStatusId));
	}

	/**
	 * Removes the contacto status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the contacto status
	 * @return the contacto status that was removed
	 * @throws com.mpwc.NoSuchContactoStatusException if a contacto status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ContactoStatus remove(Serializable primaryKey)
		throws NoSuchContactoStatusException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ContactoStatus contactoStatus = (ContactoStatus)session.get(ContactoStatusImpl.class,
					primaryKey);

			if (contactoStatus == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchContactoStatusException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(contactoStatus);
		}
		catch (NoSuchContactoStatusException nsee) {
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
	protected ContactoStatus removeImpl(ContactoStatus contactoStatus)
		throws SystemException {
		contactoStatus = toUnwrappedModel(contactoStatus);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, contactoStatus);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(contactoStatus);

		return contactoStatus;
	}

	@Override
	public ContactoStatus updateImpl(
		com.mpwc.model.ContactoStatus contactoStatus, boolean merge)
		throws SystemException {
		contactoStatus = toUnwrappedModel(contactoStatus);

		boolean isNew = contactoStatus.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, contactoStatus, merge);

			contactoStatus.setNew(false);
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

		EntityCacheUtil.putResult(ContactoStatusModelImpl.ENTITY_CACHE_ENABLED,
			ContactoStatusImpl.class, contactoStatus.getPrimaryKey(),
			contactoStatus);

		return contactoStatus;
	}

	protected ContactoStatus toUnwrappedModel(ContactoStatus contactoStatus) {
		if (contactoStatus instanceof ContactoStatusImpl) {
			return contactoStatus;
		}

		ContactoStatusImpl contactoStatusImpl = new ContactoStatusImpl();

		contactoStatusImpl.setNew(contactoStatus.isNew());
		contactoStatusImpl.setPrimaryKey(contactoStatus.getPrimaryKey());

		contactoStatusImpl.setContactStatusId(contactoStatus.getContactStatusId());
		contactoStatusImpl.setDesc_en_US(contactoStatus.getDesc_en_US());
		contactoStatusImpl.setDesc_es_ES(contactoStatus.getDesc_es_ES());
		contactoStatusImpl.setDesc_ca_ES(contactoStatus.getDesc_ca_ES());

		return contactoStatusImpl;
	}

	/**
	 * Returns the contacto status with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the contacto status
	 * @return the contacto status
	 * @throws com.liferay.portal.NoSuchModelException if a contacto status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ContactoStatus findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the contacto status with the primary key or throws a {@link com.mpwc.NoSuchContactoStatusException} if it could not be found.
	 *
	 * @param contactStatusId the primary key of the contacto status
	 * @return the contacto status
	 * @throws com.mpwc.NoSuchContactoStatusException if a contacto status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ContactoStatus findByPrimaryKey(long contactStatusId)
		throws NoSuchContactoStatusException, SystemException {
		ContactoStatus contactoStatus = fetchByPrimaryKey(contactStatusId);

		if (contactoStatus == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + contactStatusId);
			}

			throw new NoSuchContactoStatusException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				contactStatusId);
		}

		return contactoStatus;
	}

	/**
	 * Returns the contacto status with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the contacto status
	 * @return the contacto status, or <code>null</code> if a contacto status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ContactoStatus fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the contacto status with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contactStatusId the primary key of the contacto status
	 * @return the contacto status, or <code>null</code> if a contacto status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ContactoStatus fetchByPrimaryKey(long contactStatusId)
		throws SystemException {
		ContactoStatus contactoStatus = (ContactoStatus)EntityCacheUtil.getResult(ContactoStatusModelImpl.ENTITY_CACHE_ENABLED,
				ContactoStatusImpl.class, contactStatusId);

		if (contactoStatus == _nullContactoStatus) {
			return null;
		}

		if (contactoStatus == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				contactoStatus = (ContactoStatus)session.get(ContactoStatusImpl.class,
						Long.valueOf(contactStatusId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (contactoStatus != null) {
					cacheResult(contactoStatus);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ContactoStatusModelImpl.ENTITY_CACHE_ENABLED,
						ContactoStatusImpl.class, contactStatusId,
						_nullContactoStatus);
				}

				closeSession(session);
			}
		}

		return contactoStatus;
	}

	/**
	 * Returns all the contacto statuses.
	 *
	 * @return the contacto statuses
	 * @throws SystemException if a system exception occurred
	 */
	public List<ContactoStatus> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contacto statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacto statuses
	 * @param end the upper bound of the range of contacto statuses (not inclusive)
	 * @return the range of contacto statuses
	 * @throws SystemException if a system exception occurred
	 */
	public List<ContactoStatus> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the contacto statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacto statuses
	 * @param end the upper bound of the range of contacto statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of contacto statuses
	 * @throws SystemException if a system exception occurred
	 */
	public List<ContactoStatus> findAll(int start, int end,
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

		List<ContactoStatus> list = (List<ContactoStatus>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_CONTACTOSTATUS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CONTACTOSTATUS;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ContactoStatus>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ContactoStatus>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the contacto statuses from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ContactoStatus contactoStatus : findAll()) {
			remove(contactoStatus);
		}
	}

	/**
	 * Returns the number of contacto statuses.
	 *
	 * @return the number of contacto statuses
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CONTACTOSTATUS);

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
	 * Returns all the contactos associated with the contacto status.
	 *
	 * @param pk the primary key of the contacto status
	 * @return the contactos associated with the contacto status
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Contacto> getContactos(long pk)
		throws SystemException {
		return getContactos(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the contactos associated with the contacto status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the contacto status
	 * @param start the lower bound of the range of contacto statuses
	 * @param end the upper bound of the range of contacto statuses (not inclusive)
	 * @return the range of contactos associated with the contacto status
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Contacto> getContactos(long pk, int start,
		int end) throws SystemException {
		return getContactos(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_CONTACTOS = new FinderPath(com.mpwc.model.impl.ContactoModelImpl.ENTITY_CACHE_ENABLED,
			com.mpwc.model.impl.ContactoModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.ContactoImpl.class,
			com.mpwc.service.persistence.ContactoPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"getContactos",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_CONTACTOS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the contactos associated with the contacto status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the contacto status
	 * @param start the lower bound of the range of contacto statuses
	 * @param end the upper bound of the range of contacto statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of contactos associated with the contacto status
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Contacto> getContactos(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.mpwc.model.Contacto> list = (List<com.mpwc.model.Contacto>)FinderCacheUtil.getResult(FINDER_PATH_GET_CONTACTOS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETCONTACTOS.concat(ORDER_BY_CLAUSE)
										   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETCONTACTOS.concat(com.mpwc.model.impl.ContactoModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("tbl_mpwc_contacts",
					com.mpwc.model.impl.ContactoImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.mpwc.model.Contacto>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_CONTACTOS,
						finderArgs);
				}
				else {
					contactoPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_CONTACTOS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_CONTACTOS_SIZE = new FinderPath(com.mpwc.model.impl.ContactoModelImpl.ENTITY_CACHE_ENABLED,
			com.mpwc.model.impl.ContactoModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.ContactoImpl.class,
			com.mpwc.service.persistence.ContactoPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"getContactosSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_CONTACTOS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of contactos associated with the contacto status.
	 *
	 * @param pk the primary key of the contacto status
	 * @return the number of contactos associated with the contacto status
	 * @throws SystemException if a system exception occurred
	 */
	public int getContactosSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_CONTACTOS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETCONTACTOSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_CONTACTOS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_CONTACTO = new FinderPath(com.mpwc.model.impl.ContactoModelImpl.ENTITY_CACHE_ENABLED,
			com.mpwc.model.impl.ContactoModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.ContactoImpl.class,
			com.mpwc.service.persistence.ContactoPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"containsContacto",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the contacto is associated with the contacto status.
	 *
	 * @param pk the primary key of the contacto status
	 * @param contactoPK the primary key of the contacto
	 * @return <code>true</code> if the contacto is associated with the contacto status; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsContacto(long pk, long contactoPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, contactoPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_CONTACTO,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsContacto.contains(pk, contactoPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_CONTACTO,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the contacto status has any contactos associated with it.
	 *
	 * @param pk the primary key of the contacto status to check for associations with contactos
	 * @return <code>true</code> if the contacto status has any contactos associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsContactos(long pk) throws SystemException {
		if (getContactosSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Initializes the contacto status persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.mpwc.model.ContactoStatus")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ContactoStatus>> listenersList = new ArrayList<ModelListener<ContactoStatus>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ContactoStatus>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsContacto = new ContainsContacto();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ContactoStatusImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = ContactoPersistence.class)
	protected ContactoPersistence contactoPersistence;
	@BeanReference(type = ContactoStatusPersistence.class)
	protected ContactoStatusPersistence contactoStatusPersistence;
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
	protected ContainsContacto containsContacto;

	protected class ContainsContacto {
		protected ContainsContacto() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSCONTACTO,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long contactStatusId, long contactId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(contactStatusId), new Long(contactId)
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

	private static final String _SQL_SELECT_CONTACTOSTATUS = "SELECT contactoStatus FROM ContactoStatus contactoStatus";
	private static final String _SQL_COUNT_CONTACTOSTATUS = "SELECT COUNT(contactoStatus) FROM ContactoStatus contactoStatus";
	private static final String _SQL_GETCONTACTOS = "SELECT {tbl_mpwc_contacts.*} FROM tbl_mpwc_contacts INNER JOIN tbl_mpwc_contactstatus ON (tbl_mpwc_contactstatus.contactStatusId = tbl_mpwc_contacts.contactStatusId) WHERE (tbl_mpwc_contactstatus.contactStatusId = ?)";
	private static final String _SQL_GETCONTACTOSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM tbl_mpwc_contacts WHERE contactStatusId = ?";
	private static final String _SQL_CONTAINSCONTACTO = "SELECT COUNT(*) AS COUNT_VALUE FROM tbl_mpwc_contacts WHERE contactStatusId = ? AND contactId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "contactoStatus.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ContactoStatus exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(ContactoStatusPersistenceImpl.class);
	private static ContactoStatus _nullContactoStatus = new ContactoStatusImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ContactoStatus> toCacheModel() {
				return _nullContactoStatusCacheModel;
			}
		};

	private static CacheModel<ContactoStatus> _nullContactoStatusCacheModel = new CacheModel<ContactoStatus>() {
			public ContactoStatus toEntityModel() {
				return _nullContactoStatus;
			}
		};
}