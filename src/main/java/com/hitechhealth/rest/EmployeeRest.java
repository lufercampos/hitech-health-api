package com.hitechhealth.rest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.hitechhealth.facade.EmployeeFacade;
import com.hitechhealth.facade.PaginationFacade;
import com.hitechhealth.facade.UserFacade;
import com.hitechhealth.vo.EmployeeVO;
import com.hitechhealth.vo.UserVO;

@Path("employee")
public class EmployeeRest {

	@GET
	@Produces("application/json")
	@Path("getAll")
	public Response getAll(@DefaultValue("") @HeaderParam("token") String token,
			@DefaultValue("1") @QueryParam("page") int page, @DefaultValue("10") @QueryParam("pageSize") int pageSize,
			@QueryParam("whereField") List<String> whereField,
			@QueryParam("valueWhereField") List<String> valueWhereField,
			@QueryParam("typeWhereField") List<String> typeWhereField,
			@DefaultValue("") @QueryParam("orderField") String orderField,
			@DefaultValue("") @QueryParam("ordination") String ordination) throws Exception {

		try {

			UserFacade userFacade = new UserFacade();
			UserVO userVO = userFacade.getUserByToken(token);

			if (userVO != null) {

				PaginationFacade paginacaoFacade = new PaginationFacade();
				return Response.status(Response.Status.OK).entity(paginacaoFacade.getAll(EmployeeVO.class, "employee",
						page, pageSize, whereField, valueWhereField, typeWhereField, orderField, ordination)).build();

			} else {
				return Response.status(Response.Status.FORBIDDEN).entity("invalid user").build();
			}

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@GET
	@Produces("application/json")
	@Path("getById")
	public Response getById(@DefaultValue("") @HeaderParam("token") String token, @QueryParam("id") int id)
			throws Exception {

		try {
			UserFacade userFacade = new UserFacade();
			UserVO userVO = userFacade.getUserByToken(token);
			if (userVO != null) {

				EmployeeFacade employeeFacade = new EmployeeFacade();
				EmployeeVO employeeVO = employeeFacade.getById(id);

				return Response.status(Response.Status.OK).entity(employeeVO).build();
			} else {
				return Response.status(Response.Status.FORBIDDEN).entity("invalid user").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}

	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	@Path("insert")
	public Response insert(@DefaultValue("") @HeaderParam("token") String token, EmployeeVO employeeVO)
			throws Exception {
		try {
			UserFacade userFacade = new UserFacade();
			UserVO userVO = userFacade.getUserByToken(token);
			if (userVO != null) {

				EmployeeFacade employeeFacade = new EmployeeFacade();

				return Response.status(Response.Status.OK).entity(employeeFacade.insert(employeeVO)).build();
			} else {
				return Response.status(Response.Status.FORBIDDEN).entity("invalid user").build();
			}
		}catch (SQLIntegrityConstraintViolationException e ) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("There is already a employee with this code.").build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	@Path("update")
	public Response update(@DefaultValue("") @HeaderParam("token") String token, EmployeeVO employeeVO)
			throws Exception {
		try {
			UserFacade userFacade = new UserFacade();
			UserVO userVO = userFacade.getUserByToken(token);
			if (userVO != null) {

				EmployeeFacade employeeFacade = new EmployeeFacade();

				return Response.status(Response.Status.OK).entity(employeeFacade.update(employeeVO)).build();
			} else {
				return Response.status(Response.Status.FORBIDDEN).entity("invalid user").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Produces("application/json")
	@Path("delete")
	public Response delete(@DefaultValue("") @HeaderParam("token") String token,
			@QueryParam("id") int id) throws Exception {
		
		try {
			UserFacade userFacade = new UserFacade();
			UserVO userVO = userFacade.getUserByToken(token);
			if (userVO != null) {

				EmployeeFacade employeeFacade = new EmployeeFacade();
				employeeFacade.delete(id);

				return Response.status(Response.Status.OK).build();
			} else {
				return Response.status(Response.Status.FORBIDDEN).entity("invalid user").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
	}

}
