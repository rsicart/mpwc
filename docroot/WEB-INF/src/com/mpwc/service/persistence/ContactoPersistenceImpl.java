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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.mpwc.NoSuchContactoException;

import com.mpwc.model.Contacto;
import com.mpwc.model.impl.ContactoImpl;
import com.mpwc.model.impl.ContactoModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the contacto service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author rsicart
 * @see ContactoPersistence
 * @see ContactoUtil
 * @generated
 */
public class ContactoPersistenceImpl extends BasePersistenceImpl<Contacto>
	implements ContactoPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ContactoUtil} to access the contacto persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ContactoImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CTYPE = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCtype",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CTYPE = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCtype",
			new String[] { String.class.getName() },
			ContactoModelImpl.CTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CTYPE = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCtype",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_NIF = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByNif",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NIF = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByNif",
			new String[] { String.class.getName() },
			ContactoModelImpl.NIF_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NIF = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByNif",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_EMAIL = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEmail",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAIL = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEmail",
			new String[] { String.class.getName() },
			ContactoModelImpl.EMAIL_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_EMAIL = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEmail",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FIRMNAME = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFirmname",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FIRMNAME =
		new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFirmname",
			new String[] { String.class.getName() },
			ContactoModelImpl.FIRMNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FIRMNAME = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFirmname",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ZIPCODE = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByZipcode",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ZIPCODE =
		new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByZipcode",
			new String[] { String.class.getName() },
			ContactoModelImpl.ZIPCODE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ZIPCODE = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByZipcode",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				String.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { String.class.getName(), String.class.getName() },
			ContactoModelImpl.CITY_COLUMN_BITMASK |
			ContactoModelImpl.COUNTRY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, ContactoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the contacto in the entity cache if it is enabled.
	 *
	 * @param contacto the contacto
	 */
	public void cacheResult(Contacto contacto) {
		EntityCacheUtil.putResult(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoImpl.class, contacto.getPrimaryKey(), contacto);

		contacto.resetOriginalValues();
	}

	/**
	 * Caches the contactos in the entity cache if it is enabled.
	 *
	 * @param contactos the contactos
	 */
	public void cacheResult(List<Contacto> contactos) {
		for (Contacto contacto : contactos) {
			if (EntityCacheUtil.getResult(
						ContactoModelImpl.ENTITY_CACHE_ENABLED,
						ContactoImpl.class, contacto.getPrimaryKey()) == null) {
				cacheResult(contacto);
			}
			else {
				contacto.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all contactos.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ContactoImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ContactoImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the contacto.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Contacto contacto) {
		EntityCacheUtil.removeResult(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoImpl.class, contacto.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Contacto> contactos) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Contacto contacto : contactos) {
			EntityCacheUtil.removeResult(ContactoModelImpl.ENTITY_CACHE_ENABLED,
				ContactoImpl.class, contacto.getPrimaryKey());
		}
	}

	/**
	 * Creates a new contacto with the primary key. Does not add the contacto to the database.
	 *
	 * @param contactId the primary key for the new contacto
	 * @return the new contacto
	 */
	public Contacto create(long contactId) {
		Contacto contacto = new ContactoImpl();

		contacto.setNew(true);
		contacto.setPrimaryKey(contactId);

		return contacto;
	}

	/**
	 * Removes the contacto with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactId the primary key of the contacto
	 * @return the contacto that was removed
	 * @throws com.mpwc.NoSuchContactoException if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto remove(long contactId)
		throws NoSuchContactoException, SystemException {
		return remove(Long.valueOf(contactId));
	}

	/**
	 * Removes the contacto with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the contacto
	 * @return the contacto that was removed
	 * @throws com.mpwc.NoSuchContactoException if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Contacto remove(Serializable primaryKey)
		throws NoSuchContactoException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Contacto contacto = (Contacto)session.get(ContactoImpl.class,
					primaryKey);

			if (contacto == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchContactoException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(contacto);
		}
		catch (NoSuchContactoException nsee) {
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
	protected Contacto removeImpl(Contacto contacto) throws SystemException {
		contacto = toUnwrappedModel(contacto);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, contacto);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(contacto);

		return contacto;
	}

	@Override
	public Contacto updateImpl(com.mpwc.model.Contacto contacto, boolean merge)
		throws SystemException {
		contacto = toUnwrappedModel(contacto);

		boolean isNew = contacto.isNew();

		ContactoModelImpl contactoModelImpl = (ContactoModelImpl)contacto;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, contacto, merge);

			contacto.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ContactoModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((contactoModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CTYPE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						contactoModelImpl.getOriginalCtype()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CTYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CTYPE,
					args);

				args = new Object[] { contactoModelImpl.getCtype() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CTYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CTYPE,
					args);
			}

			if ((contactoModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NIF.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { contactoModelImpl.getOriginalNif() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NIF, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NIF,
					args);

				args = new Object[] { contactoModelImpl.getNif() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NIF, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NIF,
					args);
			}

			if ((contactoModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAIL.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						contactoModelImpl.getOriginalEmail()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_EMAIL, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAIL,
					args);

				args = new Object[] { contactoModelImpl.getEmail() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_EMAIL, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAIL,
					args);
			}

			if ((contactoModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FIRMNAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						contactoModelImpl.getOriginalFirmname()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FIRMNAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FIRMNAME,
					args);

				args = new Object[] { contactoModelImpl.getFirmname() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FIRMNAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FIRMNAME,
					args);
			}

			if ((contactoModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ZIPCODE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						contactoModelImpl.getOriginalZipcode()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ZIPCODE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ZIPCODE,
					args);

				args = new Object[] { contactoModelImpl.getZipcode() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ZIPCODE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ZIPCODE,
					args);
			}

			if ((contactoModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						contactoModelImpl.getOriginalCity(),
						
						contactoModelImpl.getOriginalCountry()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						contactoModelImpl.getCity(),
						
						contactoModelImpl.getCountry()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}
		}

		EntityCacheUtil.putResult(ContactoModelImpl.ENTITY_CACHE_ENABLED,
			ContactoImpl.class, contacto.getPrimaryKey(), contacto);

		return contacto;
	}

	protected Contacto toUnwrappedModel(Contacto contacto) {
		if (contacto instanceof ContactoImpl) {
			return contacto;
		}

		ContactoImpl contactoImpl = new ContactoImpl();

		contactoImpl.setNew(contacto.isNew());
		contactoImpl.setPrimaryKey(contacto.getPrimaryKey());

		contactoImpl.setContactId(contacto.getContactId());
		contactoImpl.setGroupId(contacto.getGroupId());
		contactoImpl.setCompanyId(contacto.getCompanyId());
		contactoImpl.setUserId(contacto.getUserId());
		contactoImpl.setUserName(contacto.getUserName());
		contactoImpl.setCreateDate(contacto.getCreateDate());
		contactoImpl.setModifiedDate(contacto.getModifiedDate());
		contactoImpl.setCtype(contacto.getCtype());
		contactoImpl.setNif(contacto.getNif());
		contactoImpl.setFirmname(contacto.getFirmname());
		contactoImpl.setAddress(contacto.getAddress());
		contactoImpl.setZipcode(contacto.getZipcode());
		contactoImpl.setCity(contacto.getCity());
		contactoImpl.setCountry(contacto.getCountry());
		contactoImpl.setEmail(contacto.getEmail());
		contactoImpl.setPhone(contacto.getPhone());
		contactoImpl.setPhone2(contacto.getPhone2());
		contactoImpl.setComments(contacto.getComments());
		contactoImpl.setContactStatusId(contacto.getContactStatusId());

		return contactoImpl;
	}

	/**
	 * Returns the contacto with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the contacto
	 * @return the contacto
	 * @throws com.liferay.portal.NoSuchModelException if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Contacto findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the contacto with the primary key or throws a {@link com.mpwc.NoSuchContactoException} if it could not be found.
	 *
	 * @param contactId the primary key of the contacto
	 * @return the contacto
	 * @throws com.mpwc.NoSuchContactoException if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByPrimaryKey(long contactId)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByPrimaryKey(contactId);

		if (contacto == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + contactId);
			}

			throw new NoSuchContactoException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				contactId);
		}

		return contacto;
	}

	/**
	 * Returns the contacto with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the contacto
	 * @return the contacto, or <code>null</code> if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Contacto fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the contacto with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contactId the primary key of the contacto
	 * @return the contacto, or <code>null</code> if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByPrimaryKey(long contactId) throws SystemException {
		Contacto contacto = (Contacto)EntityCacheUtil.getResult(ContactoModelImpl.ENTITY_CACHE_ENABLED,
				ContactoImpl.class, contactId);

		if (contacto == _nullContacto) {
			return null;
		}

		if (contacto == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				contacto = (Contacto)session.get(ContactoImpl.class,
						Long.valueOf(contactId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (contacto != null) {
					cacheResult(contacto);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ContactoModelImpl.ENTITY_CACHE_ENABLED,
						ContactoImpl.class, contactId, _nullContacto);
				}

				closeSession(session);
			}
		}

		return contacto;
	}

	/**
	 * Returns all the contactos where ctype = &#63;.
	 *
	 * @param ctype the ctype
	 * @return the matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByCtype(String ctype) throws SystemException {
		return findByCtype(ctype, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contactos where ctype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param ctype the ctype
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @return the range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByCtype(String ctype, int start, int end)
		throws SystemException {
		return findByCtype(ctype, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contactos where ctype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param ctype the ctype
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByCtype(String ctype, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CTYPE;
			finderArgs = new Object[] { ctype };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CTYPE;
			finderArgs = new Object[] { ctype, start, end, orderByComparator };
		}

		List<Contacto> list = (List<Contacto>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Contacto contacto : list) {
				if (!Validator.equals(ctype, contacto.getCtype())) {
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

			query.append(_SQL_SELECT_CONTACTO_WHERE);

			if (ctype == null) {
				query.append(_FINDER_COLUMN_CTYPE_CTYPE_1);
			}
			else {
				if (ctype.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_CTYPE_CTYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_CTYPE_CTYPE_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ContactoModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (ctype != null) {
					qPos.add(ctype);
				}

				list = (List<Contacto>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first contacto in the ordered set where ctype = &#63;.
	 *
	 * @param ctype the ctype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByCtype_First(String ctype,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByCtype_First(ctype, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctype=");
		msg.append(ctype);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the first contacto in the ordered set where ctype = &#63;.
	 *
	 * @param ctype the ctype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByCtype_First(String ctype,
		OrderByComparator orderByComparator) throws SystemException {
		List<Contacto> list = findByCtype(ctype, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contacto in the ordered set where ctype = &#63;.
	 *
	 * @param ctype the ctype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByCtype_Last(String ctype,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByCtype_Last(ctype, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctype=");
		msg.append(ctype);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the last contacto in the ordered set where ctype = &#63;.
	 *
	 * @param ctype the ctype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByCtype_Last(String ctype,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCtype(ctype);

		List<Contacto> list = findByCtype(ctype, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contactos before and after the current contacto in the ordered set where ctype = &#63;.
	 *
	 * @param contactId the primary key of the current contacto
	 * @param ctype the ctype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contacto
	 * @throws com.mpwc.NoSuchContactoException if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto[] findByCtype_PrevAndNext(long contactId, String ctype,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = findByPrimaryKey(contactId);

		Session session = null;

		try {
			session = openSession();

			Contacto[] array = new ContactoImpl[3];

			array[0] = getByCtype_PrevAndNext(session, contacto, ctype,
					orderByComparator, true);

			array[1] = contacto;

			array[2] = getByCtype_PrevAndNext(session, contacto, ctype,
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

	protected Contacto getByCtype_PrevAndNext(Session session,
		Contacto contacto, String ctype, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONTACTO_WHERE);

		if (ctype == null) {
			query.append(_FINDER_COLUMN_CTYPE_CTYPE_1);
		}
		else {
			if (ctype.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CTYPE_CTYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_CTYPE_CTYPE_2);
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
			query.append(ContactoModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (ctype != null) {
			qPos.add(ctype);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(contacto);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Contacto> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the contactos where nif = &#63;.
	 *
	 * @param nif the nif
	 * @return the matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByNif(String nif) throws SystemException {
		return findByNif(nif, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contactos where nif = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param nif the nif
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @return the range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByNif(String nif, int start, int end)
		throws SystemException {
		return findByNif(nif, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contactos where nif = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param nif the nif
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByNif(String nif, int start, int end,
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

		List<Contacto> list = (List<Contacto>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Contacto contacto : list) {
				if (!Validator.equals(nif, contacto.getNif())) {
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

			query.append(_SQL_SELECT_CONTACTO_WHERE);

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
				query.append(ContactoModelImpl.ORDER_BY_JPQL);
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

				list = (List<Contacto>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first contacto in the ordered set where nif = &#63;.
	 *
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByNif_First(String nif,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByNif_First(nif, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nif=");
		msg.append(nif);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the first contacto in the ordered set where nif = &#63;.
	 *
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByNif_First(String nif,
		OrderByComparator orderByComparator) throws SystemException {
		List<Contacto> list = findByNif(nif, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contacto in the ordered set where nif = &#63;.
	 *
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByNif_Last(String nif,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByNif_Last(nif, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nif=");
		msg.append(nif);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the last contacto in the ordered set where nif = &#63;.
	 *
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByNif_Last(String nif,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByNif(nif);

		List<Contacto> list = findByNif(nif, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contactos before and after the current contacto in the ordered set where nif = &#63;.
	 *
	 * @param contactId the primary key of the current contacto
	 * @param nif the nif
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contacto
	 * @throws com.mpwc.NoSuchContactoException if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto[] findByNif_PrevAndNext(long contactId, String nif,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = findByPrimaryKey(contactId);

		Session session = null;

		try {
			session = openSession();

			Contacto[] array = new ContactoImpl[3];

			array[0] = getByNif_PrevAndNext(session, contacto, nif,
					orderByComparator, true);

			array[1] = contacto;

			array[2] = getByNif_PrevAndNext(session, contacto, nif,
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

	protected Contacto getByNif_PrevAndNext(Session session, Contacto contacto,
		String nif, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONTACTO_WHERE);

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
			query.append(ContactoModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(contacto);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Contacto> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the contactos where email = &#63;.
	 *
	 * @param email the email
	 * @return the matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByEmail(String email) throws SystemException {
		return findByEmail(email, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contactos where email = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param email the email
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @return the range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByEmail(String email, int start, int end)
		throws SystemException {
		return findByEmail(email, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contactos where email = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param email the email
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByEmail(String email, int start, int end,
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

		List<Contacto> list = (List<Contacto>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Contacto contacto : list) {
				if (!Validator.equals(email, contacto.getEmail())) {
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

			query.append(_SQL_SELECT_CONTACTO_WHERE);

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
				query.append(ContactoModelImpl.ORDER_BY_JPQL);
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

				list = (List<Contacto>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first contacto in the ordered set where email = &#63;.
	 *
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByEmail_First(String email,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByEmail_First(email, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("email=");
		msg.append(email);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the first contacto in the ordered set where email = &#63;.
	 *
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByEmail_First(String email,
		OrderByComparator orderByComparator) throws SystemException {
		List<Contacto> list = findByEmail(email, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contacto in the ordered set where email = &#63;.
	 *
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByEmail_Last(String email,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByEmail_Last(email, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("email=");
		msg.append(email);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the last contacto in the ordered set where email = &#63;.
	 *
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByEmail_Last(String email,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByEmail(email);

		List<Contacto> list = findByEmail(email, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contactos before and after the current contacto in the ordered set where email = &#63;.
	 *
	 * @param contactId the primary key of the current contacto
	 * @param email the email
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contacto
	 * @throws com.mpwc.NoSuchContactoException if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto[] findByEmail_PrevAndNext(long contactId, String email,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = findByPrimaryKey(contactId);

		Session session = null;

		try {
			session = openSession();

			Contacto[] array = new ContactoImpl[3];

			array[0] = getByEmail_PrevAndNext(session, contacto, email,
					orderByComparator, true);

			array[1] = contacto;

			array[2] = getByEmail_PrevAndNext(session, contacto, email,
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

	protected Contacto getByEmail_PrevAndNext(Session session,
		Contacto contacto, String email, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONTACTO_WHERE);

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
			query.append(ContactoModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(contacto);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Contacto> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the contactos where firmname = &#63;.
	 *
	 * @param firmname the firmname
	 * @return the matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByFirmname(String firmname)
		throws SystemException {
		return findByFirmname(firmname, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the contactos where firmname = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param firmname the firmname
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @return the range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByFirmname(String firmname, int start, int end)
		throws SystemException {
		return findByFirmname(firmname, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contactos where firmname = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param firmname the firmname
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByFirmname(String firmname, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FIRMNAME;
			finderArgs = new Object[] { firmname };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FIRMNAME;
			finderArgs = new Object[] { firmname, start, end, orderByComparator };
		}

		List<Contacto> list = (List<Contacto>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Contacto contacto : list) {
				if (!Validator.equals(firmname, contacto.getFirmname())) {
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

			query.append(_SQL_SELECT_CONTACTO_WHERE);

			if (firmname == null) {
				query.append(_FINDER_COLUMN_FIRMNAME_FIRMNAME_1);
			}
			else {
				if (firmname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_FIRMNAME_FIRMNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_FIRMNAME_FIRMNAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ContactoModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (firmname != null) {
					qPos.add(firmname);
				}

				list = (List<Contacto>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first contacto in the ordered set where firmname = &#63;.
	 *
	 * @param firmname the firmname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByFirmname_First(String firmname,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByFirmname_First(firmname, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("firmname=");
		msg.append(firmname);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the first contacto in the ordered set where firmname = &#63;.
	 *
	 * @param firmname the firmname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByFirmname_First(String firmname,
		OrderByComparator orderByComparator) throws SystemException {
		List<Contacto> list = findByFirmname(firmname, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contacto in the ordered set where firmname = &#63;.
	 *
	 * @param firmname the firmname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByFirmname_Last(String firmname,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByFirmname_Last(firmname, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("firmname=");
		msg.append(firmname);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the last contacto in the ordered set where firmname = &#63;.
	 *
	 * @param firmname the firmname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByFirmname_Last(String firmname,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByFirmname(firmname);

		List<Contacto> list = findByFirmname(firmname, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contactos before and after the current contacto in the ordered set where firmname = &#63;.
	 *
	 * @param contactId the primary key of the current contacto
	 * @param firmname the firmname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contacto
	 * @throws com.mpwc.NoSuchContactoException if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto[] findByFirmname_PrevAndNext(long contactId,
		String firmname, OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = findByPrimaryKey(contactId);

		Session session = null;

		try {
			session = openSession();

			Contacto[] array = new ContactoImpl[3];

			array[0] = getByFirmname_PrevAndNext(session, contacto, firmname,
					orderByComparator, true);

			array[1] = contacto;

			array[2] = getByFirmname_PrevAndNext(session, contacto, firmname,
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

	protected Contacto getByFirmname_PrevAndNext(Session session,
		Contacto contacto, String firmname,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONTACTO_WHERE);

		if (firmname == null) {
			query.append(_FINDER_COLUMN_FIRMNAME_FIRMNAME_1);
		}
		else {
			if (firmname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_FIRMNAME_FIRMNAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_FIRMNAME_FIRMNAME_2);
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
			query.append(ContactoModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (firmname != null) {
			qPos.add(firmname);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(contacto);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Contacto> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the contactos where zipcode = &#63;.
	 *
	 * @param zipcode the zipcode
	 * @return the matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByZipcode(String zipcode)
		throws SystemException {
		return findByZipcode(zipcode, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contactos where zipcode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param zipcode the zipcode
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @return the range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByZipcode(String zipcode, int start, int end)
		throws SystemException {
		return findByZipcode(zipcode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contactos where zipcode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param zipcode the zipcode
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByZipcode(String zipcode, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ZIPCODE;
			finderArgs = new Object[] { zipcode };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ZIPCODE;
			finderArgs = new Object[] { zipcode, start, end, orderByComparator };
		}

		List<Contacto> list = (List<Contacto>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Contacto contacto : list) {
				if (!Validator.equals(zipcode, contacto.getZipcode())) {
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

			query.append(_SQL_SELECT_CONTACTO_WHERE);

			if (zipcode == null) {
				query.append(_FINDER_COLUMN_ZIPCODE_ZIPCODE_1);
			}
			else {
				if (zipcode.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_ZIPCODE_ZIPCODE_3);
				}
				else {
					query.append(_FINDER_COLUMN_ZIPCODE_ZIPCODE_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ContactoModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (zipcode != null) {
					qPos.add(zipcode);
				}

				list = (List<Contacto>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first contacto in the ordered set where zipcode = &#63;.
	 *
	 * @param zipcode the zipcode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByZipcode_First(String zipcode,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByZipcode_First(zipcode, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("zipcode=");
		msg.append(zipcode);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the first contacto in the ordered set where zipcode = &#63;.
	 *
	 * @param zipcode the zipcode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByZipcode_First(String zipcode,
		OrderByComparator orderByComparator) throws SystemException {
		List<Contacto> list = findByZipcode(zipcode, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contacto in the ordered set where zipcode = &#63;.
	 *
	 * @param zipcode the zipcode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByZipcode_Last(String zipcode,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByZipcode_Last(zipcode, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("zipcode=");
		msg.append(zipcode);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the last contacto in the ordered set where zipcode = &#63;.
	 *
	 * @param zipcode the zipcode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByZipcode_Last(String zipcode,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByZipcode(zipcode);

		List<Contacto> list = findByZipcode(zipcode, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contactos before and after the current contacto in the ordered set where zipcode = &#63;.
	 *
	 * @param contactId the primary key of the current contacto
	 * @param zipcode the zipcode
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contacto
	 * @throws com.mpwc.NoSuchContactoException if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto[] findByZipcode_PrevAndNext(long contactId, String zipcode,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = findByPrimaryKey(contactId);

		Session session = null;

		try {
			session = openSession();

			Contacto[] array = new ContactoImpl[3];

			array[0] = getByZipcode_PrevAndNext(session, contacto, zipcode,
					orderByComparator, true);

			array[1] = contacto;

			array[2] = getByZipcode_PrevAndNext(session, contacto, zipcode,
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

	protected Contacto getByZipcode_PrevAndNext(Session session,
		Contacto contacto, String zipcode, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONTACTO_WHERE);

		if (zipcode == null) {
			query.append(_FINDER_COLUMN_ZIPCODE_ZIPCODE_1);
		}
		else {
			if (zipcode.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_ZIPCODE_ZIPCODE_3);
			}
			else {
				query.append(_FINDER_COLUMN_ZIPCODE_ZIPCODE_2);
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
			query.append(ContactoModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (zipcode != null) {
			qPos.add(zipcode);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(contacto);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Contacto> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the contactos where city = &#63; and country = &#63;.
	 *
	 * @param city the city
	 * @param country the country
	 * @return the matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByC_C(String city, String country)
		throws SystemException {
		return findByC_C(city, country, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the contactos where city = &#63; and country = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param city the city
	 * @param country the country
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @return the range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByC_C(String city, String country, int start,
		int end) throws SystemException {
		return findByC_C(city, country, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contactos where city = &#63; and country = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param city the city
	 * @param country the country
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findByC_C(String city, String country, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { city, country };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					city, country,
					
					start, end, orderByComparator
				};
		}

		List<Contacto> list = (List<Contacto>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Contacto contacto : list) {
				if (!Validator.equals(city, contacto.getCity()) ||
						!Validator.equals(country, contacto.getCountry())) {
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

			query.append(_SQL_SELECT_CONTACTO_WHERE);

			if (city == null) {
				query.append(_FINDER_COLUMN_C_C_CITY_1);
			}
			else {
				if (city.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_CITY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_CITY_2);
				}
			}

			if (country == null) {
				query.append(_FINDER_COLUMN_C_C_COUNTRY_1);
			}
			else {
				if (country.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_COUNTRY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_COUNTRY_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ContactoModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (city != null) {
					qPos.add(city);
				}

				if (country != null) {
					qPos.add(country);
				}

				list = (List<Contacto>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first contacto in the ordered set where city = &#63; and country = &#63;.
	 *
	 * @param city the city
	 * @param country the country
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByC_C_First(String city, String country,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByC_C_First(city, country, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("city=");
		msg.append(city);

		msg.append(", country=");
		msg.append(country);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the first contacto in the ordered set where city = &#63; and country = &#63;.
	 *
	 * @param city the city
	 * @param country the country
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByC_C_First(String city, String country,
		OrderByComparator orderByComparator) throws SystemException {
		List<Contacto> list = findByC_C(city, country, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contacto in the ordered set where city = &#63; and country = &#63;.
	 *
	 * @param city the city
	 * @param country the country
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto
	 * @throws com.mpwc.NoSuchContactoException if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto findByC_C_Last(String city, String country,
		OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = fetchByC_C_Last(city, country, orderByComparator);

		if (contacto != null) {
			return contacto;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("city=");
		msg.append(city);

		msg.append(", country=");
		msg.append(country);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContactoException(msg.toString());
	}

	/**
	 * Returns the last contacto in the ordered set where city = &#63; and country = &#63;.
	 *
	 * @param city the city
	 * @param country the country
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacto, or <code>null</code> if a matching contacto could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto fetchByC_C_Last(String city, String country,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_C(city, country);

		List<Contacto> list = findByC_C(city, country, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contactos before and after the current contacto in the ordered set where city = &#63; and country = &#63;.
	 *
	 * @param contactId the primary key of the current contacto
	 * @param city the city
	 * @param country the country
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contacto
	 * @throws com.mpwc.NoSuchContactoException if a contacto with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Contacto[] findByC_C_PrevAndNext(long contactId, String city,
		String country, OrderByComparator orderByComparator)
		throws NoSuchContactoException, SystemException {
		Contacto contacto = findByPrimaryKey(contactId);

		Session session = null;

		try {
			session = openSession();

			Contacto[] array = new ContactoImpl[3];

			array[0] = getByC_C_PrevAndNext(session, contacto, city, country,
					orderByComparator, true);

			array[1] = contacto;

			array[2] = getByC_C_PrevAndNext(session, contacto, city, country,
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

	protected Contacto getByC_C_PrevAndNext(Session session, Contacto contacto,
		String city, String country, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONTACTO_WHERE);

		if (city == null) {
			query.append(_FINDER_COLUMN_C_C_CITY_1);
		}
		else {
			if (city.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_C_CITY_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_C_CITY_2);
			}
		}

		if (country == null) {
			query.append(_FINDER_COLUMN_C_C_COUNTRY_1);
		}
		else {
			if (country.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_C_COUNTRY_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_C_COUNTRY_2);
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
			query.append(ContactoModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (city != null) {
			qPos.add(city);
		}

		if (country != null) {
			qPos.add(country);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(contacto);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Contacto> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the contactos.
	 *
	 * @return the contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contactos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @return the range of contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the contactos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of contactos
	 * @throws SystemException if a system exception occurred
	 */
	public List<Contacto> findAll(int start, int end,
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

		List<Contacto> list = (List<Contacto>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_CONTACTO);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CONTACTO.concat(ContactoModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Contacto>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Contacto>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the contactos where ctype = &#63; from the database.
	 *
	 * @param ctype the ctype
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCtype(String ctype) throws SystemException {
		for (Contacto contacto : findByCtype(ctype)) {
			remove(contacto);
		}
	}

	/**
	 * Removes all the contactos where nif = &#63; from the database.
	 *
	 * @param nif the nif
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByNif(String nif) throws SystemException {
		for (Contacto contacto : findByNif(nif)) {
			remove(contacto);
		}
	}

	/**
	 * Removes all the contactos where email = &#63; from the database.
	 *
	 * @param email the email
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByEmail(String email) throws SystemException {
		for (Contacto contacto : findByEmail(email)) {
			remove(contacto);
		}
	}

	/**
	 * Removes all the contactos where firmname = &#63; from the database.
	 *
	 * @param firmname the firmname
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByFirmname(String firmname) throws SystemException {
		for (Contacto contacto : findByFirmname(firmname)) {
			remove(contacto);
		}
	}

	/**
	 * Removes all the contactos where zipcode = &#63; from the database.
	 *
	 * @param zipcode the zipcode
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByZipcode(String zipcode) throws SystemException {
		for (Contacto contacto : findByZipcode(zipcode)) {
			remove(contacto);
		}
	}

	/**
	 * Removes all the contactos where city = &#63; and country = &#63; from the database.
	 *
	 * @param city the city
	 * @param country the country
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(String city, String country)
		throws SystemException {
		for (Contacto contacto : findByC_C(city, country)) {
			remove(contacto);
		}
	}

	/**
	 * Removes all the contactos from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Contacto contacto : findAll()) {
			remove(contacto);
		}
	}

	/**
	 * Returns the number of contactos where ctype = &#63;.
	 *
	 * @param ctype the ctype
	 * @return the number of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCtype(String ctype) throws SystemException {
		Object[] finderArgs = new Object[] { ctype };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CTYPE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONTACTO_WHERE);

			if (ctype == null) {
				query.append(_FINDER_COLUMN_CTYPE_CTYPE_1);
			}
			else {
				if (ctype.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_CTYPE_CTYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_CTYPE_CTYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (ctype != null) {
					qPos.add(ctype);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CTYPE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of contactos where nif = &#63;.
	 *
	 * @param nif the nif
	 * @return the number of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public int countByNif(String nif) throws SystemException {
		Object[] finderArgs = new Object[] { nif };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NIF,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONTACTO_WHERE);

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
	 * Returns the number of contactos where email = &#63;.
	 *
	 * @param email the email
	 * @return the number of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public int countByEmail(String email) throws SystemException {
		Object[] finderArgs = new Object[] { email };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_EMAIL,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONTACTO_WHERE);

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
	 * Returns the number of contactos where firmname = &#63;.
	 *
	 * @param firmname the firmname
	 * @return the number of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFirmname(String firmname) throws SystemException {
		Object[] finderArgs = new Object[] { firmname };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FIRMNAME,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONTACTO_WHERE);

			if (firmname == null) {
				query.append(_FINDER_COLUMN_FIRMNAME_FIRMNAME_1);
			}
			else {
				if (firmname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_FIRMNAME_FIRMNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_FIRMNAME_FIRMNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (firmname != null) {
					qPos.add(firmname);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FIRMNAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of contactos where zipcode = &#63;.
	 *
	 * @param zipcode the zipcode
	 * @return the number of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public int countByZipcode(String zipcode) throws SystemException {
		Object[] finderArgs = new Object[] { zipcode };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ZIPCODE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONTACTO_WHERE);

			if (zipcode == null) {
				query.append(_FINDER_COLUMN_ZIPCODE_ZIPCODE_1);
			}
			else {
				if (zipcode.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_ZIPCODE_ZIPCODE_3);
				}
				else {
					query.append(_FINDER_COLUMN_ZIPCODE_ZIPCODE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (zipcode != null) {
					qPos.add(zipcode);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ZIPCODE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of contactos where city = &#63; and country = &#63;.
	 *
	 * @param city the city
	 * @param country the country
	 * @return the number of matching contactos
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(String city, String country)
		throws SystemException {
		Object[] finderArgs = new Object[] { city, country };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CONTACTO_WHERE);

			if (city == null) {
				query.append(_FINDER_COLUMN_C_C_CITY_1);
			}
			else {
				if (city.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_CITY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_CITY_2);
				}
			}

			if (country == null) {
				query.append(_FINDER_COLUMN_C_C_COUNTRY_1);
			}
			else {
				if (country.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_C_COUNTRY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_C_COUNTRY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (city != null) {
					qPos.add(city);
				}

				if (country != null) {
					qPos.add(country);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of contactos.
	 *
	 * @return the number of contactos
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CONTACTO);

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
	 * Returns all the projects associated with the contacto.
	 *
	 * @param pk the primary key of the contacto
	 * @return the projects associated with the contacto
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Project> getProjects(long pk)
		throws SystemException {
		return getProjects(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the projects associated with the contacto.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the contacto
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @return the range of projects associated with the contacto
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Project> getProjects(long pk, int start, int end)
		throws SystemException {
		return getProjects(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_PROJECTS = new FinderPath(com.mpwc.model.impl.ProjectModelImpl.ENTITY_CACHE_ENABLED,
			com.mpwc.model.impl.ProjectModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.ProjectImpl.class,
			com.mpwc.service.persistence.ProjectPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"getProjects",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_PROJECTS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the projects associated with the contacto.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the contacto
	 * @param start the lower bound of the range of contactos
	 * @param end the upper bound of the range of contactos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of projects associated with the contacto
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
			com.mpwc.model.impl.ProjectModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.ProjectImpl.class,
			com.mpwc.service.persistence.ProjectPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"getProjectsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_PROJECTS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of projects associated with the contacto.
	 *
	 * @param pk the primary key of the contacto
	 * @return the number of projects associated with the contacto
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
			com.mpwc.model.impl.ProjectModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.ProjectImpl.class,
			com.mpwc.service.persistence.ProjectPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"containsProject",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the project is associated with the contacto.
	 *
	 * @param pk the primary key of the contacto
	 * @param projectPK the primary key of the project
	 * @return <code>true</code> if the project is associated with the contacto; <code>false</code> otherwise
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
	 * Returns <code>true</code> if the contacto has any projects associated with it.
	 *
	 * @param pk the primary key of the contacto to check for associations with projects
	 * @return <code>true</code> if the contacto has any projects associated with it; <code>false</code> otherwise
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
	 * Initializes the contacto persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.mpwc.model.Contacto")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Contacto>> listenersList = new ArrayList<ModelListener<Contacto>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Contacto>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsProject = new ContainsProject();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ContactoImpl.class.getName());
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
	protected ContainsProject containsProject;

	protected class ContainsProject {
		protected ContainsProject() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSPROJECT,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long contactId, long projectId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(contactId), new Long(projectId)
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

	private static final String _SQL_SELECT_CONTACTO = "SELECT contacto FROM Contacto contacto";
	private static final String _SQL_SELECT_CONTACTO_WHERE = "SELECT contacto FROM Contacto contacto WHERE ";
	private static final String _SQL_COUNT_CONTACTO = "SELECT COUNT(contacto) FROM Contacto contacto";
	private static final String _SQL_COUNT_CONTACTO_WHERE = "SELECT COUNT(contacto) FROM Contacto contacto WHERE ";
	private static final String _SQL_GETPROJECTS = "SELECT {tbl_mpwc_projects.*} FROM tbl_mpwc_projects INNER JOIN tbl_mpwc_contacts ON (tbl_mpwc_contacts.contactId = tbl_mpwc_projects.contactId) WHERE (tbl_mpwc_contacts.contactId = ?)";
	private static final String _SQL_GETPROJECTSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM tbl_mpwc_projects WHERE contactId = ?";
	private static final String _SQL_CONTAINSPROJECT = "SELECT COUNT(*) AS COUNT_VALUE FROM tbl_mpwc_projects WHERE contactId = ? AND projectId = ?";
	private static final String _FINDER_COLUMN_CTYPE_CTYPE_1 = "contacto.ctype IS NULL";
	private static final String _FINDER_COLUMN_CTYPE_CTYPE_2 = "contacto.ctype = ?";
	private static final String _FINDER_COLUMN_CTYPE_CTYPE_3 = "(contacto.ctype IS NULL OR contacto.ctype = ?)";
	private static final String _FINDER_COLUMN_NIF_NIF_1 = "contacto.nif IS NULL";
	private static final String _FINDER_COLUMN_NIF_NIF_2 = "contacto.nif = ?";
	private static final String _FINDER_COLUMN_NIF_NIF_3 = "(contacto.nif IS NULL OR contacto.nif = ?)";
	private static final String _FINDER_COLUMN_EMAIL_EMAIL_1 = "contacto.email IS NULL";
	private static final String _FINDER_COLUMN_EMAIL_EMAIL_2 = "contacto.email = ?";
	private static final String _FINDER_COLUMN_EMAIL_EMAIL_3 = "(contacto.email IS NULL OR contacto.email = ?)";
	private static final String _FINDER_COLUMN_FIRMNAME_FIRMNAME_1 = "contacto.firmname IS NULL";
	private static final String _FINDER_COLUMN_FIRMNAME_FIRMNAME_2 = "contacto.firmname = ?";
	private static final String _FINDER_COLUMN_FIRMNAME_FIRMNAME_3 = "(contacto.firmname IS NULL OR contacto.firmname = ?)";
	private static final String _FINDER_COLUMN_ZIPCODE_ZIPCODE_1 = "contacto.zipcode IS NULL";
	private static final String _FINDER_COLUMN_ZIPCODE_ZIPCODE_2 = "contacto.zipcode = ?";
	private static final String _FINDER_COLUMN_ZIPCODE_ZIPCODE_3 = "(contacto.zipcode IS NULL OR contacto.zipcode = ?)";
	private static final String _FINDER_COLUMN_C_C_CITY_1 = "contacto.city IS NULL AND ";
	private static final String _FINDER_COLUMN_C_C_CITY_2 = "contacto.city = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CITY_3 = "(contacto.city IS NULL OR contacto.city = ?) AND ";
	private static final String _FINDER_COLUMN_C_C_COUNTRY_1 = "contacto.country IS NULL";
	private static final String _FINDER_COLUMN_C_C_COUNTRY_2 = "contacto.country = ?";
	private static final String _FINDER_COLUMN_C_C_COUNTRY_3 = "(contacto.country IS NULL OR contacto.country = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "contacto.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Contacto exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Contacto exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(ContactoPersistenceImpl.class);
	private static Contacto _nullContacto = new ContactoImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Contacto> toCacheModel() {
				return _nullContactoCacheModel;
			}
		};

	private static CacheModel<Contacto> _nullContactoCacheModel = new CacheModel<Contacto>() {
			public Contacto toEntityModel() {
				return _nullContacto;
			}
		};
}