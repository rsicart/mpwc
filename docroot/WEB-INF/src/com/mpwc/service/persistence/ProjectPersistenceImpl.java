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

import com.mpwc.NoSuchProjectException;

import com.mpwc.model.Project;
import com.mpwc.model.impl.ProjectImpl;
import com.mpwc.model.impl.ProjectModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the project service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author rsicart
 * @see ProjectPersistence
 * @see ProjectUtil
 * @generated
 */
public class ProjectPersistenceImpl extends BasePersistenceImpl<Project>
	implements ProjectPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ProjectUtil} to access the project persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ProjectImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NAME = new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, ProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_Name",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NAME =
		new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, ProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_Name",
			new String[] { Long.class.getName(), String.class.getName() },
			ProjectModelImpl.GROUPID_COLUMN_BITMASK |
			ProjectModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_NAME = new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_Name",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_TYPE = new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, ProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_Type",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_TYPE =
		new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, ProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_Type",
			new String[] { Long.class.getName(), String.class.getName() },
			ProjectModelImpl.GROUPID_COLUMN_BITMASK |
			ProjectModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_TYPE = new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_Type",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, ProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, ProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			ProjectModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, ProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, ProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			ProjectModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, ProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, ProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the project in the entity cache if it is enabled.
	 *
	 * @param project the project
	 */
	public void cacheResult(Project project) {
		EntityCacheUtil.putResult(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectImpl.class, project.getPrimaryKey(), project);

		project.resetOriginalValues();
	}

	/**
	 * Caches the projects in the entity cache if it is enabled.
	 *
	 * @param projects the projects
	 */
	public void cacheResult(List<Project> projects) {
		for (Project project : projects) {
			if (EntityCacheUtil.getResult(
						ProjectModelImpl.ENTITY_CACHE_ENABLED,
						ProjectImpl.class, project.getPrimaryKey()) == null) {
				cacheResult(project);
			}
			else {
				project.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all projects.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ProjectImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ProjectImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the project.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Project project) {
		EntityCacheUtil.removeResult(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectImpl.class, project.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Project> projects) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Project project : projects) {
			EntityCacheUtil.removeResult(ProjectModelImpl.ENTITY_CACHE_ENABLED,
				ProjectImpl.class, project.getPrimaryKey());
		}
	}

	/**
	 * Creates a new project with the primary key. Does not add the project to the database.
	 *
	 * @param projectId the primary key for the new project
	 * @return the new project
	 */
	public Project create(long projectId) {
		Project project = new ProjectImpl();

		project.setNew(true);
		project.setPrimaryKey(projectId);

		return project;
	}

	/**
	 * Removes the project with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param projectId the primary key of the project
	 * @return the project that was removed
	 * @throws com.mpwc.NoSuchProjectException if a project with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project remove(long projectId)
		throws NoSuchProjectException, SystemException {
		return remove(Long.valueOf(projectId));
	}

	/**
	 * Removes the project with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the project
	 * @return the project that was removed
	 * @throws com.mpwc.NoSuchProjectException if a project with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Project remove(Serializable primaryKey)
		throws NoSuchProjectException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Project project = (Project)session.get(ProjectImpl.class, primaryKey);

			if (project == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProjectException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(project);
		}
		catch (NoSuchProjectException nsee) {
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
	protected Project removeImpl(Project project) throws SystemException {
		project = toUnwrappedModel(project);

		try {
			clearWorkers.clear(project.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, project);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(project);

		return project;
	}

	@Override
	public Project updateImpl(com.mpwc.model.Project project, boolean merge)
		throws SystemException {
		project = toUnwrappedModel(project);

		boolean isNew = project.isNew();

		ProjectModelImpl projectModelImpl = (ProjectModelImpl)project;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, project, merge);

			project.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ProjectModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((projectModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(projectModelImpl.getOriginalGroupId()),
						
						projectModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_NAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NAME,
					args);

				args = new Object[] {
						Long.valueOf(projectModelImpl.getGroupId()),
						
						projectModelImpl.getName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_NAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_NAME,
					args);
			}

			if ((projectModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_TYPE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(projectModelImpl.getOriginalGroupId()),
						
						projectModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_TYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_TYPE,
					args);

				args = new Object[] {
						Long.valueOf(projectModelImpl.getGroupId()),
						
						projectModelImpl.getType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_TYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_TYPE,
					args);
			}

			if ((projectModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(projectModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { Long.valueOf(projectModelImpl.getGroupId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((projectModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(projectModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(projectModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		EntityCacheUtil.putResult(ProjectModelImpl.ENTITY_CACHE_ENABLED,
			ProjectImpl.class, project.getPrimaryKey(), project);

		return project;
	}

	protected Project toUnwrappedModel(Project project) {
		if (project instanceof ProjectImpl) {
			return project;
		}

		ProjectImpl projectImpl = new ProjectImpl();

		projectImpl.setNew(project.isNew());
		projectImpl.setPrimaryKey(project.getPrimaryKey());

		projectImpl.setProjectId(project.getProjectId());
		projectImpl.setGroupId(project.getGroupId());
		projectImpl.setCompanyId(project.getCompanyId());
		projectImpl.setCreateDate(project.getCreateDate());
		projectImpl.setModifiedDate(project.getModifiedDate());
		projectImpl.setName(project.getName());
		projectImpl.setType(project.getType());
		projectImpl.setDescShort(project.getDescShort());
		projectImpl.setDescFull(project.getDescFull());
		projectImpl.setCostEstimatedEuros(project.getCostEstimatedEuros());
		projectImpl.setTimeEstimatedHours(project.getTimeEstimatedHours());
		projectImpl.setCanSetWorkerHours(project.isCanSetWorkerHours());
		projectImpl.setStartDate(project.getStartDate());
		projectImpl.setEndDate(project.getEndDate());
		projectImpl.setComments(project.getComments());
		projectImpl.setProjectStatusId(project.getProjectStatusId());
		projectImpl.setWorkerId(project.getWorkerId());
		projectImpl.setContactoId(project.getContactoId());

		return projectImpl;
	}

	/**
	 * Returns the project with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the project
	 * @return the project
	 * @throws com.liferay.portal.NoSuchModelException if a project with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Project findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the project with the primary key or throws a {@link com.mpwc.NoSuchProjectException} if it could not be found.
	 *
	 * @param projectId the primary key of the project
	 * @return the project
	 * @throws com.mpwc.NoSuchProjectException if a project with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project findByPrimaryKey(long projectId)
		throws NoSuchProjectException, SystemException {
		Project project = fetchByPrimaryKey(projectId);

		if (project == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + projectId);
			}

			throw new NoSuchProjectException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				projectId);
		}

		return project;
	}

	/**
	 * Returns the project with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the project
	 * @return the project, or <code>null</code> if a project with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Project fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the project with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param projectId the primary key of the project
	 * @return the project, or <code>null</code> if a project with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project fetchByPrimaryKey(long projectId) throws SystemException {
		Project project = (Project)EntityCacheUtil.getResult(ProjectModelImpl.ENTITY_CACHE_ENABLED,
				ProjectImpl.class, projectId);

		if (project == _nullProject) {
			return null;
		}

		if (project == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				project = (Project)session.get(ProjectImpl.class,
						Long.valueOf(projectId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (project != null) {
					cacheResult(project);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ProjectModelImpl.ENTITY_CACHE_ENABLED,
						ProjectImpl.class, projectId, _nullProject);
				}

				closeSession(session);
			}
		}

		return project;
	}

	/**
	 * Returns all the projects where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByG_Name(long groupId, String name)
		throws SystemException {
		return findByG_Name(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the projects where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @return the range of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByG_Name(long groupId, String name, int start,
		int end) throws SystemException {
		return findByG_Name(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the projects where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByG_Name(long groupId, String name, int start,
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

		List<Project> list = (List<Project>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Project project : list) {
				if ((groupId != project.getGroupId()) ||
						!Validator.equals(name, project.getName())) {
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

			query.append(_SQL_SELECT_PROJECT_WHERE);

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
				query.append(ProjectModelImpl.ORDER_BY_JPQL);
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

				list = (List<Project>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first project in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching project
	 * @throws com.mpwc.NoSuchProjectException if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project findByG_Name_First(long groupId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = fetchByG_Name_First(groupId, name, orderByComparator);

		if (project != null) {
			return project;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProjectException(msg.toString());
	}

	/**
	 * Returns the first project in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching project, or <code>null</code> if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project fetchByG_Name_First(long groupId, String name,
		OrderByComparator orderByComparator) throws SystemException {
		List<Project> list = findByG_Name(groupId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last project in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching project
	 * @throws com.mpwc.NoSuchProjectException if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project findByG_Name_Last(long groupId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = fetchByG_Name_Last(groupId, name, orderByComparator);

		if (project != null) {
			return project;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProjectException(msg.toString());
	}

	/**
	 * Returns the last project in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching project, or <code>null</code> if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project fetchByG_Name_Last(long groupId, String name,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_Name(groupId, name);

		List<Project> list = findByG_Name(groupId, name, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the projects before and after the current project in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param projectId the primary key of the current project
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next project
	 * @throws com.mpwc.NoSuchProjectException if a project with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project[] findByG_Name_PrevAndNext(long projectId, long groupId,
		String name, OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = findByPrimaryKey(projectId);

		Session session = null;

		try {
			session = openSession();

			Project[] array = new ProjectImpl[3];

			array[0] = getByG_Name_PrevAndNext(session, project, groupId, name,
					orderByComparator, true);

			array[1] = project;

			array[2] = getByG_Name_PrevAndNext(session, project, groupId, name,
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

	protected Project getByG_Name_PrevAndNext(Session session, Project project,
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

		query.append(_SQL_SELECT_PROJECT_WHERE);

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
			query.append(ProjectModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(project);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Project> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the projects where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByG_Type(long groupId, String type)
		throws SystemException {
		return findByG_Type(groupId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the projects where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @return the range of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByG_Type(long groupId, String type, int start,
		int end) throws SystemException {
		return findByG_Type(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the projects where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByG_Type(long groupId, String type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_TYPE;
			finderArgs = new Object[] { groupId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_TYPE;
			finderArgs = new Object[] {
					groupId, type,
					
					start, end, orderByComparator
				};
		}

		List<Project> list = (List<Project>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Project project : list) {
				if ((groupId != project.getGroupId()) ||
						!Validator.equals(type, project.getType())) {
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

			query.append(_SQL_SELECT_PROJECT_WHERE);

			query.append(_FINDER_COLUMN_G_TYPE_GROUPID_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_G_TYPE_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_TYPE_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_TYPE_TYPE_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ProjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (type != null) {
					qPos.add(type);
				}

				list = (List<Project>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first project in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching project
	 * @throws com.mpwc.NoSuchProjectException if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project findByG_Type_First(long groupId, String type,
		OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = fetchByG_Type_First(groupId, type, orderByComparator);

		if (project != null) {
			return project;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProjectException(msg.toString());
	}

	/**
	 * Returns the first project in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching project, or <code>null</code> if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project fetchByG_Type_First(long groupId, String type,
		OrderByComparator orderByComparator) throws SystemException {
		List<Project> list = findByG_Type(groupId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last project in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching project
	 * @throws com.mpwc.NoSuchProjectException if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project findByG_Type_Last(long groupId, String type,
		OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = fetchByG_Type_Last(groupId, type, orderByComparator);

		if (project != null) {
			return project;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProjectException(msg.toString());
	}

	/**
	 * Returns the last project in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching project, or <code>null</code> if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project fetchByG_Type_Last(long groupId, String type,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_Type(groupId, type);

		List<Project> list = findByG_Type(groupId, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the projects before and after the current project in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param projectId the primary key of the current project
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next project
	 * @throws com.mpwc.NoSuchProjectException if a project with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project[] findByG_Type_PrevAndNext(long projectId, long groupId,
		String type, OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = findByPrimaryKey(projectId);

		Session session = null;

		try {
			session = openSession();

			Project[] array = new ProjectImpl[3];

			array[0] = getByG_Type_PrevAndNext(session, project, groupId, type,
					orderByComparator, true);

			array[1] = project;

			array[2] = getByG_Type_PrevAndNext(session, project, groupId, type,
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

	protected Project getByG_Type_PrevAndNext(Session session, Project project,
		long groupId, String type, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PROJECT_WHERE);

		query.append(_FINDER_COLUMN_G_TYPE_GROUPID_2);

		if (type == null) {
			query.append(_FINDER_COLUMN_G_TYPE_TYPE_1);
		}
		else {
			if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_TYPE_TYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_TYPE_TYPE_2);
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
			query.append(ProjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (type != null) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(project);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Project> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the projects where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByGroupId(long groupId) throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the projects where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @return the range of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the projects where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<Project> list = (List<Project>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Project project : list) {
				if ((groupId != project.getGroupId())) {
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

			query.append(_SQL_SELECT_PROJECT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ProjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<Project>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first project in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching project
	 * @throws com.mpwc.NoSuchProjectException if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = fetchByGroupId_First(groupId, orderByComparator);

		if (project != null) {
			return project;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProjectException(msg.toString());
	}

	/**
	 * Returns the first project in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching project, or <code>null</code> if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Project> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last project in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching project
	 * @throws com.mpwc.NoSuchProjectException if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = fetchByGroupId_Last(groupId, orderByComparator);

		if (project != null) {
			return project;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProjectException(msg.toString());
	}

	/**
	 * Returns the last project in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching project, or <code>null</code> if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<Project> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the projects before and after the current project in the ordered set where groupId = &#63;.
	 *
	 * @param projectId the primary key of the current project
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next project
	 * @throws com.mpwc.NoSuchProjectException if a project with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project[] findByGroupId_PrevAndNext(long projectId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = findByPrimaryKey(projectId);

		Session session = null;

		try {
			session = openSession();

			Project[] array = new ProjectImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, project, groupId,
					orderByComparator, true);

			array[1] = project;

			array[2] = getByGroupId_PrevAndNext(session, project, groupId,
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

	protected Project getByGroupId_PrevAndNext(Session session,
		Project project, long groupId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PROJECT_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(ProjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(project);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Project> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the projects where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the projects where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @return the range of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the projects where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<Project> list = (List<Project>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Project project : list) {
				if ((companyId != project.getCompanyId())) {
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

			query.append(_SQL_SELECT_PROJECT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ProjectModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Project>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first project in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching project
	 * @throws com.mpwc.NoSuchProjectException if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = fetchByCompanyId_First(companyId, orderByComparator);

		if (project != null) {
			return project;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProjectException(msg.toString());
	}

	/**
	 * Returns the first project in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching project, or <code>null</code> if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Project> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last project in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching project
	 * @throws com.mpwc.NoSuchProjectException if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = fetchByCompanyId_Last(companyId, orderByComparator);

		if (project != null) {
			return project;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProjectException(msg.toString());
	}

	/**
	 * Returns the last project in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching project, or <code>null</code> if a matching project could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<Project> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the projects before and after the current project in the ordered set where companyId = &#63;.
	 *
	 * @param projectId the primary key of the current project
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next project
	 * @throws com.mpwc.NoSuchProjectException if a project with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Project[] findByCompanyId_PrevAndNext(long projectId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchProjectException, SystemException {
		Project project = findByPrimaryKey(projectId);

		Session session = null;

		try {
			session = openSession();

			Project[] array = new ProjectImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, project, companyId,
					orderByComparator, true);

			array[1] = project;

			array[2] = getByCompanyId_PrevAndNext(session, project, companyId,
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

	protected Project getByCompanyId_PrevAndNext(Session session,
		Project project, long companyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PROJECT_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(ProjectModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(project);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Project> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the projects.
	 *
	 * @return the projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @return the range of projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of projects
	 * @throws SystemException if a system exception occurred
	 */
	public List<Project> findAll(int start, int end,
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

		List<Project> list = (List<Project>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PROJECT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PROJECT.concat(ProjectModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Project>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Project>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the projects where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_Name(long groupId, String name)
		throws SystemException {
		for (Project project : findByG_Name(groupId, name)) {
			remove(project);
		}
	}

	/**
	 * Removes all the projects where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_Type(long groupId, String type)
		throws SystemException {
		for (Project project : findByG_Type(groupId, type)) {
			remove(project);
		}
	}

	/**
	 * Removes all the projects where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (Project project : findByGroupId(groupId)) {
			remove(project);
		}
	}

	/**
	 * Removes all the projects where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (Project project : findByCompanyId(companyId)) {
			remove(project);
		}
	}

	/**
	 * Removes all the projects from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Project project : findAll()) {
			remove(project);
		}
	}

	/**
	 * Returns the number of projects where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_Name(long groupId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_NAME,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PROJECT_WHERE);

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
	 * Returns the number of projects where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_Type(long groupId, String type)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_TYPE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PROJECT_WHERE);

			query.append(_FINDER_COLUMN_G_TYPE_GROUPID_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_G_TYPE_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_TYPE_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_TYPE_TYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (type != null) {
					qPos.add(type);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_TYPE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of projects where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PROJECT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of projects where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching projects
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PROJECT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of projects.
	 *
	 * @return the number of projects
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PROJECT);

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
	 * Returns all the workers associated with the project.
	 *
	 * @param pk the primary key of the project
	 * @return the workers associated with the project
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Worker> getWorkers(long pk)
		throws SystemException {
		return getWorkers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the workers associated with the project.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the project
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @return the range of workers associated with the project
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.Worker> getWorkers(long pk, int start, int end)
		throws SystemException {
		return getWorkers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_WORKERS = new FinderPath(com.mpwc.model.impl.WorkerModelImpl.ENTITY_CACHE_ENABLED,
			ProjectModelImpl.FINDER_CACHE_ENABLED_TOOLS_TBL_MPWC_WORKER_PROJECT,
			com.mpwc.model.impl.WorkerImpl.class,
			ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME,
			"getWorkers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_WORKERS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the workers associated with the project.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the project
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of workers associated with the project
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
			ProjectModelImpl.FINDER_CACHE_ENABLED_TOOLS_TBL_MPWC_WORKER_PROJECT,
			Long.class,
			ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME,
			"getWorkersSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_WORKERS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of workers associated with the project.
	 *
	 * @param pk the primary key of the project
	 * @return the number of workers associated with the project
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
			ProjectModelImpl.FINDER_CACHE_ENABLED_TOOLS_TBL_MPWC_WORKER_PROJECT,
			Boolean.class,
			ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME,
			"containsWorker",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the worker is associated with the project.
	 *
	 * @param pk the primary key of the project
	 * @param workerPK the primary key of the worker
	 * @return <code>true</code> if the worker is associated with the project; <code>false</code> otherwise
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
	 * Returns <code>true</code> if the project has any workers associated with it.
	 *
	 * @param pk the primary key of the project to check for associations with workers
	 * @return <code>true</code> if the project has any workers associated with it; <code>false</code> otherwise
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
	 * Adds an association between the project and the worker. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the project
	 * @param workerPK the primary key of the worker
	 * @throws SystemException if a system exception occurred
	 */
	public void addWorker(long pk, long workerPK) throws SystemException {
		try {
			addWorker.add(pk, workerPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Adds an association between the project and the worker. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the project
	 * @param worker the worker
	 * @throws SystemException if a system exception occurred
	 */
	public void addWorker(long pk, com.mpwc.model.Worker worker)
		throws SystemException {
		try {
			addWorker.add(pk, worker.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Adds an association between the project and the workers. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the project
	 * @param workerPKs the primary keys of the workers
	 * @throws SystemException if a system exception occurred
	 */
	public void addWorkers(long pk, long[] workerPKs) throws SystemException {
		try {
			for (long workerPK : workerPKs) {
				addWorker.add(pk, workerPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Adds an association between the project and the workers. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the project
	 * @param workers the workers
	 * @throws SystemException if a system exception occurred
	 */
	public void addWorkers(long pk, List<com.mpwc.model.Worker> workers)
		throws SystemException {
		try {
			for (com.mpwc.model.Worker worker : workers) {
				addWorker.add(pk, worker.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Clears all associations between the project and its workers. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the project to clear the associated workers from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearWorkers(long pk) throws SystemException {
		try {
			clearWorkers.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Removes the association between the project and the worker. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the project
	 * @param workerPK the primary key of the worker
	 * @throws SystemException if a system exception occurred
	 */
	public void removeWorker(long pk, long workerPK) throws SystemException {
		try {
			removeWorker.remove(pk, workerPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Removes the association between the project and the worker. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the project
	 * @param worker the worker
	 * @throws SystemException if a system exception occurred
	 */
	public void removeWorker(long pk, com.mpwc.model.Worker worker)
		throws SystemException {
		try {
			removeWorker.remove(pk, worker.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Removes the association between the project and the workers. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the project
	 * @param workerPKs the primary keys of the workers
	 * @throws SystemException if a system exception occurred
	 */
	public void removeWorkers(long pk, long[] workerPKs)
		throws SystemException {
		try {
			for (long workerPK : workerPKs) {
				removeWorker.remove(pk, workerPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Removes the association between the project and the workers. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the project
	 * @param workers the workers
	 * @throws SystemException if a system exception occurred
	 */
	public void removeWorkers(long pk, List<com.mpwc.model.Worker> workers)
		throws SystemException {
		try {
			for (com.mpwc.model.Worker worker : workers) {
				removeWorker.remove(pk, worker.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Sets the workers associated with the project, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the project
	 * @param workerPKs the primary keys of the workers to be associated with the project
	 * @throws SystemException if a system exception occurred
	 */
	public void setWorkers(long pk, long[] workerPKs) throws SystemException {
		try {
			Set<Long> workerPKSet = SetUtil.fromArray(workerPKs);

			List<com.mpwc.model.Worker> workers = getWorkers(pk);

			for (com.mpwc.model.Worker worker : workers) {
				if (!workerPKSet.remove(worker.getPrimaryKey())) {
					removeWorker.remove(pk, worker.getPrimaryKey());
				}
			}

			for (Long workerPK : workerPKSet) {
				addWorker.add(pk, workerPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Sets the workers associated with the project, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the project
	 * @param workers the workers to be associated with the project
	 * @throws SystemException if a system exception occurred
	 */
	public void setWorkers(long pk, List<com.mpwc.model.Worker> workers)
		throws SystemException {
		try {
			long[] workerPKs = new long[workers.size()];

			for (int i = 0; i < workers.size(); i++) {
				com.mpwc.model.Worker worker = workers.get(i);

				workerPKs[i] = worker.getPrimaryKey();
			}

			setWorkers(pk, workerPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(ProjectModelImpl.MAPPING_TABLE_TOOLS_TBL_MPWC_WORKER_PROJECT_NAME);
		}
	}

	/**
	 * Returns all the time boxs associated with the project.
	 *
	 * @param pk the primary key of the project
	 * @return the time boxs associated with the project
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.TimeBox> getTimeBoxs(long pk)
		throws SystemException {
		return getTimeBoxs(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the time boxs associated with the project.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the project
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @return the range of time boxs associated with the project
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.TimeBox> getTimeBoxs(long pk, int start, int end)
		throws SystemException {
		return getTimeBoxs(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_TIMEBOXS = new FinderPath(com.mpwc.model.impl.TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			com.mpwc.model.impl.TimeBoxModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.TimeBoxImpl.class,
			com.mpwc.service.persistence.TimeBoxPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"getTimeBoxs",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_TIMEBOXS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the time boxs associated with the project.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the project
	 * @param start the lower bound of the range of projects
	 * @param end the upper bound of the range of projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of time boxs associated with the project
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.mpwc.model.TimeBox> getTimeBoxs(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.mpwc.model.TimeBox> list = (List<com.mpwc.model.TimeBox>)FinderCacheUtil.getResult(FINDER_PATH_GET_TIMEBOXS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETTIMEBOXS.concat(ORDER_BY_CLAUSE)
										  .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETTIMEBOXS;
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("tbl_mpwc_timebox",
					com.mpwc.model.impl.TimeBoxImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.mpwc.model.TimeBox>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_TIMEBOXS,
						finderArgs);
				}
				else {
					timeBoxPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_TIMEBOXS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_TIMEBOXS_SIZE = new FinderPath(com.mpwc.model.impl.TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			com.mpwc.model.impl.TimeBoxModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.TimeBoxImpl.class,
			com.mpwc.service.persistence.TimeBoxPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"getTimeBoxsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_TIMEBOXS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of time boxs associated with the project.
	 *
	 * @param pk the primary key of the project
	 * @return the number of time boxs associated with the project
	 * @throws SystemException if a system exception occurred
	 */
	public int getTimeBoxsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_TIMEBOXS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETTIMEBOXSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_TIMEBOXS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_TIMEBOX = new FinderPath(com.mpwc.model.impl.TimeBoxModelImpl.ENTITY_CACHE_ENABLED,
			com.mpwc.model.impl.TimeBoxModelImpl.FINDER_CACHE_ENABLED,
			com.mpwc.model.impl.TimeBoxImpl.class,
			com.mpwc.service.persistence.TimeBoxPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"containsTimeBox",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the time box is associated with the project.
	 *
	 * @param pk the primary key of the project
	 * @param timeBoxPK the primary key of the time box
	 * @return <code>true</code> if the time box is associated with the project; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsTimeBox(long pk, long timeBoxPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, timeBoxPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_TIMEBOX,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsTimeBox.contains(pk, timeBoxPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_TIMEBOX,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the project has any time boxs associated with it.
	 *
	 * @param pk the primary key of the project to check for associations with time boxs
	 * @return <code>true</code> if the project has any time boxs associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsTimeBoxs(long pk) throws SystemException {
		if (getTimeBoxsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Initializes the project persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.mpwc.model.Project")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Project>> listenersList = new ArrayList<ModelListener<Project>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Project>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsWorker = new ContainsWorker();

		addWorker = new AddWorker();
		clearWorkers = new ClearWorkers();
		removeWorker = new RemoveWorker();

		containsTimeBox = new ContainsTimeBox();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ProjectImpl.class.getName());
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
	protected ContainsWorker containsWorker;
	protected AddWorker addWorker;
	protected ClearWorkers clearWorkers;
	protected RemoveWorker removeWorker;
	protected ContainsTimeBox containsTimeBox;

	protected class ContainsWorker {
		protected ContainsWorker() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSWORKER,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long projectId, long workerId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(projectId), new Long(workerId)
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

	protected class AddWorker {
		protected AddWorker() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO tools_tbl_mpwc_worker_project (projectId, workerId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long projectId, long workerId)
			throws SystemException {
			if (!containsWorker.contains(projectId, workerId)) {
				ModelListener<com.mpwc.model.Worker>[] workerListeners = workerPersistence.getListeners();

				for (ModelListener<Project> listener : listeners) {
					listener.onBeforeAddAssociation(projectId,
						com.mpwc.model.Worker.class.getName(), workerId);
				}

				for (ModelListener<com.mpwc.model.Worker> listener : workerListeners) {
					listener.onBeforeAddAssociation(workerId,
						Project.class.getName(), projectId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(projectId), new Long(workerId)
					});

				for (ModelListener<Project> listener : listeners) {
					listener.onAfterAddAssociation(projectId,
						com.mpwc.model.Worker.class.getName(), workerId);
				}

				for (ModelListener<com.mpwc.model.Worker> listener : workerListeners) {
					listener.onAfterAddAssociation(workerId,
						Project.class.getName(), projectId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearWorkers {
		protected ClearWorkers() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM tools_tbl_mpwc_worker_project WHERE projectId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long projectId) throws SystemException {
			ModelListener<com.mpwc.model.Worker>[] workerListeners = workerPersistence.getListeners();

			List<com.mpwc.model.Worker> workers = null;

			if ((listeners.length > 0) || (workerListeners.length > 0)) {
				workers = getWorkers(projectId);

				for (com.mpwc.model.Worker worker : workers) {
					for (ModelListener<Project> listener : listeners) {
						listener.onBeforeRemoveAssociation(projectId,
							com.mpwc.model.Worker.class.getName(),
							worker.getPrimaryKey());
					}

					for (ModelListener<com.mpwc.model.Worker> listener : workerListeners) {
						listener.onBeforeRemoveAssociation(worker.getPrimaryKey(),
							Project.class.getName(), projectId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(projectId) });

			if ((listeners.length > 0) || (workerListeners.length > 0)) {
				for (com.mpwc.model.Worker worker : workers) {
					for (ModelListener<Project> listener : listeners) {
						listener.onAfterRemoveAssociation(projectId,
							com.mpwc.model.Worker.class.getName(),
							worker.getPrimaryKey());
					}

					for (ModelListener<com.mpwc.model.Worker> listener : workerListeners) {
						listener.onAfterRemoveAssociation(worker.getPrimaryKey(),
							Project.class.getName(), projectId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveWorker {
		protected RemoveWorker() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM tools_tbl_mpwc_worker_project WHERE projectId = ? AND workerId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long projectId, long workerId)
			throws SystemException {
			if (containsWorker.contains(projectId, workerId)) {
				ModelListener<com.mpwc.model.Worker>[] workerListeners = workerPersistence.getListeners();

				for (ModelListener<Project> listener : listeners) {
					listener.onBeforeRemoveAssociation(projectId,
						com.mpwc.model.Worker.class.getName(), workerId);
				}

				for (ModelListener<com.mpwc.model.Worker> listener : workerListeners) {
					listener.onBeforeRemoveAssociation(workerId,
						Project.class.getName(), projectId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(projectId), new Long(workerId)
					});

				for (ModelListener<Project> listener : listeners) {
					listener.onAfterRemoveAssociation(projectId,
						com.mpwc.model.Worker.class.getName(), workerId);
				}

				for (ModelListener<com.mpwc.model.Worker> listener : workerListeners) {
					listener.onAfterRemoveAssociation(workerId,
						Project.class.getName(), projectId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsTimeBox {
		protected ContainsTimeBox() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSTIMEBOX,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long projectId, long timeboxId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(projectId), new Long(timeboxId)
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

	private static final String _SQL_SELECT_PROJECT = "SELECT project FROM Project project";
	private static final String _SQL_SELECT_PROJECT_WHERE = "SELECT project FROM Project project WHERE ";
	private static final String _SQL_COUNT_PROJECT = "SELECT COUNT(project) FROM Project project";
	private static final String _SQL_COUNT_PROJECT_WHERE = "SELECT COUNT(project) FROM Project project WHERE ";
	private static final String _SQL_GETWORKERS = "SELECT {tbl_mpwc_workers.*} FROM tbl_mpwc_workers INNER JOIN tools_tbl_mpwc_worker_project ON (tools_tbl_mpwc_worker_project.workerId = tbl_mpwc_workers.workerId) WHERE (tools_tbl_mpwc_worker_project.projectId = ?)";
	private static final String _SQL_GETWORKERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM tools_tbl_mpwc_worker_project WHERE projectId = ?";
	private static final String _SQL_CONTAINSWORKER = "SELECT COUNT(*) AS COUNT_VALUE FROM tools_tbl_mpwc_worker_project WHERE projectId = ? AND workerId = ?";
	private static final String _SQL_GETTIMEBOXS = "SELECT {tbl_mpwc_timebox.*} FROM tbl_mpwc_timebox INNER JOIN tbl_mpwc_projects ON (tbl_mpwc_projects.projectId = tbl_mpwc_timebox.projectId) WHERE (tbl_mpwc_projects.projectId = ?)";
	private static final String _SQL_GETTIMEBOXSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM tbl_mpwc_timebox WHERE projectId = ?";
	private static final String _SQL_CONTAINSTIMEBOX = "SELECT COUNT(*) AS COUNT_VALUE FROM tbl_mpwc_timebox WHERE projectId = ? AND timeboxId = ?";
	private static final String _FINDER_COLUMN_G_NAME_GROUPID_2 = "project.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_NAME_NAME_1 = "project.name IS NULL";
	private static final String _FINDER_COLUMN_G_NAME_NAME_2 = "project.name = ?";
	private static final String _FINDER_COLUMN_G_NAME_NAME_3 = "(project.name IS NULL OR project.name = ?)";
	private static final String _FINDER_COLUMN_G_TYPE_GROUPID_2 = "project.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_TYPE_TYPE_1 = "project.type IS NULL";
	private static final String _FINDER_COLUMN_G_TYPE_TYPE_2 = "project.type = ?";
	private static final String _FINDER_COLUMN_G_TYPE_TYPE_3 = "(project.type IS NULL OR project.type = ?)";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "project.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "project.companyId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "project.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Project exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Project exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(ProjectPersistenceImpl.class);
	private static Project _nullProject = new ProjectImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Project> toCacheModel() {
				return _nullProjectCacheModel;
			}
		};

	private static CacheModel<Project> _nullProjectCacheModel = new CacheModel<Project>() {
			public Project toEntityModel() {
				return _nullProject;
			}
		};
}