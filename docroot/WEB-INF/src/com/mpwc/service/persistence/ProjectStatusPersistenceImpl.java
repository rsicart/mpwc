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

import com.mpwc.NoSuchProjectStatusException;

import com.mpwc.model.ProjectStatus;
import com.mpwc.model.impl.ProjectStatusImpl;
import com.mpwc.model.impl.ProjectStatusModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the project status service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author rsicart
 * @see ProjectStatusPersistence
 * @see ProjectStatusUtil
 * @generated
 */
public class ProjectStatusPersistenceImpl extends BasePersistenceImpl<ProjectStatus>
	implements ProjectStatusPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ProjectStatusUtil} to access the project status persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ProjectStatusImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ProjectStatusModelImpl.ENTITY_CACHE_ENABLED,
			ProjectStatusModelImpl.FINDER_CACHE_ENABLED,
			ProjectStatusImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ProjectStatusModelImpl.ENTITY_CACHE_ENABLED,
			ProjectStatusModelImpl.FINDER_CACHE_ENABLED,
			ProjectStatusImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ProjectStatusModelImpl.ENTITY_CACHE_ENABLED,
			ProjectStatusModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the project status in the entity cache if it is enabled.
	 *
	 * @param projectStatus the project status
	 */
	public void cacheResult(ProjectStatus projectStatus) {
		EntityCacheUtil.putResult(ProjectStatusModelImpl.ENTITY_CACHE_ENABLED,
			ProjectStatusImpl.class, projectStatus.getPrimaryKey(),
			projectStatus);

		projectStatus.resetOriginalValues();
	}

	/**
	 * Caches the project statuses in the entity cache if it is enabled.
	 *
	 * @param projectStatuses the project statuses
	 */
	public void cacheResult(List<ProjectStatus> projectStatuses) {
		for (ProjectStatus projectStatus : projectStatuses) {
			if (EntityCacheUtil.getResult(
						ProjectStatusModelImpl.ENTITY_CACHE_ENABLED,
						ProjectStatusImpl.class, projectStatus.getPrimaryKey()) == null) {
				cacheResult(projectStatus);
			}
			else {
				projectStatus.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all project statuses.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ProjectStatusImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ProjectStatusImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the project status.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ProjectStatus projectStatus) {
		EntityCacheUtil.removeResult(ProjectStatusModelImpl.ENTITY_CACHE_ENABLED,
			ProjectStatusImpl.class, projectStatus.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ProjectStatus> projectStatuses) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ProjectStatus projectStatus : projectStatuses) {
			EntityCacheUtil.removeResult(ProjectStatusModelImpl.ENTITY_CACHE_ENABLED,
				ProjectStatusImpl.class, projectStatus.getPrimaryKey());
		}
	}

	/**
	 * Creates a new project status with the primary key. Does not add the project status to the database.
	 *
	 * @param projectStatusId the primary key for the new project status
	 * @return the new project status
	 */
	public ProjectStatus create(long projectStatusId) {
		ProjectStatus projectStatus = new ProjectStatusImpl();

		projectStatus.setNew(true);
		projectStatus.setPrimaryKey(projectStatusId);

		return projectStatus;
	}

	/**
	 * Removes the project status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param projectStatusId the primary key of the project status
	 * @return the project status that was removed
	 * @throws com.mpwc.NoSuchProjectStatusException if a project status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ProjectStatus remove(long projectStatusId)
		throws NoSuchProjectStatusException, SystemException {
		return remove(Long.valueOf(projectStatusId));
	}

	/**
	 * Removes the project status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the project status
	 * @return the project status that was removed
	 * @throws com.mpwc.NoSuchProjectStatusException if a project status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ProjectStatus remove(Serializable primaryKey)
		throws NoSuchProjectStatusException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ProjectStatus projectStatus = (ProjectStatus)session.get(ProjectStatusImpl.class,
					primaryKey);

			if (projectStatus == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProjectStatusException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(projectStatus);
		}
		catch (NoSuchProjectStatusException nsee) {
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
	protected ProjectStatus removeImpl(ProjectStatus projectStatus)
		throws SystemException {
		projectStatus = toUnwrappedModel(projectStatus);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, projectStatus);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(projectStatus);

		return projectStatus;
	}

	@Override
	public ProjectStatus updateImpl(
		com.mpwc.model.ProjectStatus projectStatus, boolean merge)
		throws SystemException {
		projectStatus = toUnwrappedModel(projectStatus);

		boolean isNew = projectStatus.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, projectStatus, merge);

			projectStatus.setNew(false);
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

		EntityCacheUtil.putResult(ProjectStatusModelImpl.ENTITY_CACHE_ENABLED,
			ProjectStatusImpl.class, projectStatus.getPrimaryKey(),
			projectStatus);

		return projectStatus;
	}

	protected ProjectStatus toUnwrappedModel(ProjectStatus projectStatus) {
		if (projectStatus instanceof ProjectStatusImpl) {
			return projectStatus;
		}

		ProjectStatusImpl projectStatusImpl = new ProjectStatusImpl();

		projectStatusImpl.setNew(projectStatus.isNew());
		projectStatusImpl.setPrimaryKey(projectStatus.getPrimaryKey());

		projectStatusImpl.setProjectStatusId(projectStatus.getProjectStatusId());
		projectStatusImpl.setDesc_en_US(projectStatus.getDesc_en_US());
		projectStatusImpl.setDesc_es_ES(projectStatus.getDesc_es_ES());
		projectStatusImpl.setDesc_ca_ES(projectStatus.getDesc_ca_ES());

		return projectStatusImpl;
	}

	/**
	 * Returns the project status with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the project status
	 * @return the project status
	 * @throws com.liferay.portal.NoSuchModelException if a project status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ProjectStatus findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the project status with the primary key or throws a {@link com.mpwc.NoSuchProjectStatusException} if it could not be found.
	 *
	 * @param projectStatusId the primary key of the project status
	 * @return the project status
	 * @throws com.mpwc.NoSuchProjectStatusException if a project status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ProjectStatus findByPrimaryKey(long projectStatusId)
		throws NoSuchProjectStatusException, SystemException {
		ProjectStatus projectStatus = fetchByPrimaryKey(projectStatusId);

		if (projectStatus == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + projectStatusId);
			}

			throw new NoSuchProjectStatusException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				projectStatusId);
		}

		return projectStatus;
	}

	/**
	 * Returns the project status with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the project status
	 * @return the project status, or <code>null</code> if a project status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ProjectStatus fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the project status with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param projectStatusId the primary key of the project status
	 * @return the project status, or <code>null</code> if a project status with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ProjectStatus fetchByPrimaryKey(long projectStatusId)
		throws SystemException {
		ProjectStatus projectStatus = (ProjectStatus)EntityCacheUtil.getResult(ProjectStatusModelImpl.ENTITY_CACHE_ENABLED,
				ProjectStatusImpl.class, projectStatusId);

		if (projectStatus == _nullProjectStatus) {
			return null;
		}

		if (projectStatus == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				projectStatus = (ProjectStatus)session.get(ProjectStatusImpl.class,
						Long.valueOf(projectStatusId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (projectStatus != null) {
					cacheResult(projectStatus);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ProjectStatusModelImpl.ENTITY_CACHE_ENABLED,
						ProjectStatusImpl.class, projectStatusId,
						_nullProjectStatus);
				}

				closeSession(session);
			}
		}

		return projectStatus;
	}

	/**
	 * Returns all the project statuses.
	 *
	 * @return the project statuses
	 * @throws SystemException if a system exception occurred
	 */
	public List<ProjectStatus> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the project statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of project statuses
	 * @param end the upper bound of the range of project statuses (not inclusive)
	 * @return the range of project statuses
	 * @throws SystemException if a system exception occurred
	 */
	public List<ProjectStatus> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the project statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of project statuses
	 * @param end the upper bound of the range of project statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of project statuses
	 * @throws SystemException if a system exception occurred
	 */
	public List<ProjectStatus> findAll(int start, int end,
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

		List<ProjectStatus> list = (List<ProjectStatus>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PROJECTSTATUS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PROJECTSTATUS;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ProjectStatus>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ProjectStatus>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the project statuses from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ProjectStatus projectStatus : findAll()) {
			remove(projectStatus);
		}
	}

	/**
	 * Returns the number of project statuses.
	 *
	 * @return the number of project statuses
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PROJECTSTATUS);

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
	 * Returns all the projects associated with the project status.
	 *
	 * @param pk the primary key of the project status
	 * @return the projects associated with the project status
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Project> getProjects(long pk)
		throws SystemException {
		return getProjects(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the projects associated with the project status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the project status
	 * @param start the lower bound of the range of project statuses
	 * @param end the upper bound of the range of project statuses (not inclusive)
	 * @return the range of projects associated with the project status
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
	 * Returns an ordered range of all the projects associated with the project status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the project status
	 * @param start the lower bound of the range of project statuses
	 * @param end the upper bound of the range of project statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of projects associated with the project status
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
	 * Returns the number of projects associated with the project status.
	 *
	 * @param pk the primary key of the project status
	 * @return the number of projects associated with the project status
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
	 * Returns <code>true</code> if the project is associated with the project status.
	 *
	 * @param pk the primary key of the project status
	 * @param projectPK the primary key of the project
	 * @return <code>true</code> if the project is associated with the project status; <code>false</code> otherwise
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
	 * Returns <code>true</code> if the project status has any projects associated with it.
	 *
	 * @param pk the primary key of the project status to check for associations with projects
	 * @return <code>true</code> if the project status has any projects associated with it; <code>false</code> otherwise
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
	 * Initializes the project status persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.mpwc.model.ProjectStatus")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ProjectStatus>> listenersList = new ArrayList<ModelListener<ProjectStatus>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ProjectStatus>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(ProjectStatusImpl.class.getName());
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

		protected boolean contains(long projectStatusId, long projectId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(projectStatusId), new Long(projectId)
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

	private static final String _SQL_SELECT_PROJECTSTATUS = "SELECT projectStatus FROM ProjectStatus projectStatus";
	private static final String _SQL_COUNT_PROJECTSTATUS = "SELECT COUNT(projectStatus) FROM ProjectStatus projectStatus";
	private static final String _SQL_GETPROJECTS = "SELECT {tbl_mpwc_projects.*} FROM tbl_mpwc_projects INNER JOIN tbl_mpwc_projectstatus ON (tbl_mpwc_projectstatus.projectStatusId = tbl_mpwc_projects.projectStatusId) WHERE (tbl_mpwc_projectstatus.projectStatusId = ?)";
	private static final String _SQL_GETPROJECTSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM tbl_mpwc_projects WHERE projectStatusId = ?";
	private static final String _SQL_CONTAINSPROJECT = "SELECT COUNT(*) AS COUNT_VALUE FROM tbl_mpwc_projects WHERE projectStatusId = ? AND projectId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "projectStatus.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ProjectStatus exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(ProjectStatusPersistenceImpl.class);
	private static ProjectStatus _nullProjectStatus = new ProjectStatusImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ProjectStatus> toCacheModel() {
				return _nullProjectStatusCacheModel;
			}
		};

	private static CacheModel<ProjectStatus> _nullProjectStatusCacheModel = new CacheModel<ProjectStatus>() {
			public ProjectStatus toEntityModel() {
				return _nullProjectStatus;
			}
		};
}