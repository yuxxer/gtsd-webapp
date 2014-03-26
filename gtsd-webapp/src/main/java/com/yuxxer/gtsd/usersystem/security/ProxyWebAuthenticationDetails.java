/*
 * @(#)ProxyWebUserAuthenticationDetailsSource.java 0.1 2010-4-6
 * Copyright 2004-2007 Homolo Co., Ltd. All rights reserved.
 */

package com.yuxxer.gtsd.usersystem.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.homolo.framework.util.ServletUtils;

public class ProxyWebAuthenticationDetails extends WebAuthenticationDetails {

	private static final long serialVersionUID = 7725271591194672003L;

	private final String agent;

	// ~ Constructors ===================================================================================================

	/**
	 * Records the remote address and will also set the session Id if a session already exists (it won't create one).
	 * 
	 * @param request
	 *            that the authentication request was received from
	 */
	public ProxyWebAuthenticationDetails(HttpServletRequest request) {
		super(new ProxyHttpServletRequest(request));
		agent = request.getHeader("User-Agent");
	}

	public String getAgent() {
		return agent;
	}

	static class ProxyHttpServletRequest implements HttpServletRequest {

		private HttpServletRequest delegate;

		public ProxyHttpServletRequest(HttpServletRequest request) {
			delegate = request;
		}

		/**
		 * @param name
		 * @return
		 * @see javax.servlet.ServletRequest#getAttribute(java.lang.String)
		 */
		public Object getAttribute(String name) {
			return delegate.getAttribute(name);
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getAuthType()
		 */
		public String getAuthType() {
			return delegate.getAuthType();
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getCookies()
		 */
		public Cookie[] getCookies() {
			return delegate.getCookies();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getAttributeNames()
		 */
		public Enumeration<String> getAttributeNames() {
			return delegate.getAttributeNames();
		}

		/**
		 * @param name
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getDateHeader(java.lang.String)
		 */
		public long getDateHeader(String name) {
			return delegate.getDateHeader(name);
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getCharacterEncoding()
		 */
		public String getCharacterEncoding() {
			return delegate.getCharacterEncoding();
		}

		/**
		 * @param env
		 * @throws UnsupportedEncodingException
		 * @see javax.servlet.ServletRequest#setCharacterEncoding(java.lang.String)
		 */
		public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
			delegate.setCharacterEncoding(env);
		}

		/**
		 * @param name
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getHeader(java.lang.String)
		 */
		public String getHeader(String name) {
			return delegate.getHeader(name);
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getContentLength()
		 */
		public int getContentLength() {
			return delegate.getContentLength();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getContentType()
		 */
		public String getContentType() {
			return delegate.getContentType();
		}

		/**
		 * @param name
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getHeaders(java.lang.String)
		 */
		public Enumeration<String> getHeaders(String name) {
			return delegate.getHeaders(name);
		}

		/**
		 * @return
		 * @throws IOException
		 * @see javax.servlet.ServletRequest#getInputStream()
		 */
		public ServletInputStream getInputStream() throws IOException {
			return delegate.getInputStream();
		}

		/**
		 * @param name
		 * @return
		 * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
		 */
		public String getParameter(String name) {
			return delegate.getParameter(name);
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
		 */
		public Enumeration<String> getHeaderNames() {
			return delegate.getHeaderNames();
		}

		/**
		 * @param name
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getIntHeader(java.lang.String)
		 */
		public int getIntHeader(String name) {
			return delegate.getIntHeader(name);
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getParameterNames()
		 */
		public Enumeration<String> getParameterNames() {
			return delegate.getParameterNames();
		}

		/**
		 * @param name
		 * @return
		 * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
		 */
		public String[] getParameterValues(String name) {
			return delegate.getParameterValues(name);
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getMethod()
		 */
		public String getMethod() {
			return delegate.getMethod();
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getPathInfo()
		 */
		public String getPathInfo() {
			return delegate.getPathInfo();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getParameterMap()
		 */
		public Map<String, String[]> getParameterMap() {
			return delegate.getParameterMap();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getProtocol()
		 */
		public String getProtocol() {
			return delegate.getProtocol();
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getPathTranslated()
		 */
		public String getPathTranslated() {
			return delegate.getPathTranslated();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getScheme()
		 */
		public String getScheme() {
			return delegate.getScheme();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getServerName()
		 */
		public String getServerName() {
			return delegate.getServerName();
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getContextPath()
		 */
		public String getContextPath() {
			return delegate.getContextPath();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getServerPort()
		 */
		public int getServerPort() {
			return delegate.getServerPort();
		}

		/**
		 * @return
		 * @throws IOException
		 * @see javax.servlet.ServletRequest#getReader()
		 */
		public BufferedReader getReader() throws IOException {
			return delegate.getReader();
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getQueryString()
		 */
		public String getQueryString() {
			return delegate.getQueryString();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getRemoteAddr()
		 */
		public String getRemoteAddr() {
			return ServletUtils.getRemoteAddr(delegate);
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getRemoteUser()
		 */
		public String getRemoteUser() {
			return delegate.getRemoteUser();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getRemoteHost()
		 */
		public String getRemoteHost() {
			return delegate.getRemoteHost();
		}

		/**
		 * @param role
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#isUserInRole(java.lang.String)
		 */
		public boolean isUserInRole(String role) {
			return delegate.isUserInRole(role);
		}

		/**
		 * @param name
		 * @param o
		 * @see javax.servlet.ServletRequest#setAttribute(java.lang.String, java.lang.Object)
		 */
		public void setAttribute(String name, Object o) {
			delegate.setAttribute(name, o);
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
		 */
		public Principal getUserPrincipal() {
			return delegate.getUserPrincipal();
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
		 */
		public String getRequestedSessionId() {
			return delegate.getRequestedSessionId();
		}

		/**
		 * @param name
		 * @see javax.servlet.ServletRequest#removeAttribute(java.lang.String)
		 */
		public void removeAttribute(String name) {
			delegate.removeAttribute(name);
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getRequestURI()
		 */
		public String getRequestURI() {
			return delegate.getRequestURI();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getLocale()
		 */
		public Locale getLocale() {
			return delegate.getLocale();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getLocales()
		 */
		public Enumeration<Locale> getLocales() {
			return delegate.getLocales();
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getRequestURL()
		 */
		public StringBuffer getRequestURL() {
			return delegate.getRequestURL();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#isSecure()
		 */
		public boolean isSecure() {
			return delegate.isSecure();
		}

		/**
		 * @param path
		 * @return
		 * @see javax.servlet.ServletRequest#getRequestDispatcher(java.lang.String)
		 */
		public RequestDispatcher getRequestDispatcher(String path) {
			return delegate.getRequestDispatcher(path);
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getServletPath()
		 */
		public String getServletPath() {
			return delegate.getServletPath();
		}

		/**
		 * @param create
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
		 */
		public HttpSession getSession(boolean create) {
			return delegate.getSession(create);
		}

		/**
		 * @param path
		 * @return
		 * @deprecated
		 * @see javax.servlet.ServletRequest#getRealPath(java.lang.String)
		 */
		public String getRealPath(String path) {
			return delegate.getRealPath(path);
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getRemotePort()
		 */
		public int getRemotePort() {
			return delegate.getRemotePort();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getLocalName()
		 */
		public String getLocalName() {
			return delegate.getLocalName();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getLocalAddr()
		 */
		public String getLocalAddr() {
			return delegate.getLocalAddr();
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#getSession()
		 */
		public HttpSession getSession() {
			return delegate.getSession();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getLocalPort()
		 */
		public int getLocalPort() {
			return delegate.getLocalPort();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getServletContext()
		 */
		public ServletContext getServletContext() {
			return delegate.getServletContext();
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdValid()
		 */
		public boolean isRequestedSessionIdValid() {
			return delegate.isRequestedSessionIdValid();
		}

		/**
		 * @return
		 * @throws IllegalStateException
		 * @see javax.servlet.ServletRequest#startAsync()
		 */
		public AsyncContext startAsync() throws IllegalStateException {
			return delegate.startAsync();
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromCookie()
		 */
		public boolean isRequestedSessionIdFromCookie() {
			return delegate.isRequestedSessionIdFromCookie();
		}

		/**
		 * @return
		 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromURL()
		 */
		public boolean isRequestedSessionIdFromURL() {
			return delegate.isRequestedSessionIdFromURL();
		}

		/**
		 * @return
		 * @deprecated
		 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
		 */
		public boolean isRequestedSessionIdFromUrl() {
			return delegate.isRequestedSessionIdFromUrl();
		}

		/**
		 * @param response
		 * @return
		 * @throws IOException
		 * @throws ServletException
		 * @see javax.servlet.http.HttpServletRequest#authenticate(javax.servlet.http.HttpServletResponse)
		 */
		public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
			return delegate.authenticate(response);
		}

		/**
		 * @param servletRequest
		 * @param servletResponse
		 * @return
		 * @throws IllegalStateException
		 * @see javax.servlet.ServletRequest#startAsync(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
		 */
		public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
			return delegate.startAsync(servletRequest, servletResponse);
		}

		/**
		 * @param username
		 * @param password
		 * @throws ServletException
		 * @see javax.servlet.http.HttpServletRequest#login(java.lang.String, java.lang.String)
		 */
		public void login(String username, String password) throws ServletException {
			delegate.login(username, password);
		}

		/**
		 * @throws ServletException
		 * @see javax.servlet.http.HttpServletRequest#logout()
		 */
		public void logout() throws ServletException {
			delegate.logout();
		}

		/**
		 * @return
		 * @throws IOException
		 * @throws ServletException
		 * @see javax.servlet.http.HttpServletRequest#getParts()
		 */
		public Collection<Part> getParts() throws IOException, ServletException {
			return delegate.getParts();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#isAsyncStarted()
		 */
		public boolean isAsyncStarted() {
			return delegate.isAsyncStarted();
		}

		/**
		 * @param name
		 * @return
		 * @throws IOException
		 * @throws ServletException
		 * @see javax.servlet.http.HttpServletRequest#getPart(java.lang.String)
		 */
		public Part getPart(String name) throws IOException, ServletException {
			return delegate.getPart(name);
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#isAsyncSupported()
		 */
		public boolean isAsyncSupported() {
			return delegate.isAsyncSupported();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getAsyncContext()
		 */
		public AsyncContext getAsyncContext() {
			return delegate.getAsyncContext();
		}

		/**
		 * @return
		 * @see javax.servlet.ServletRequest#getDispatcherType()
		 */
		public DispatcherType getDispatcherType() {
			return delegate.getDispatcherType();
		}

	}
}
