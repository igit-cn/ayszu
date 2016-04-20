/*
 * Copyright 2015 Welyab da Silva Paula
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.welyab.ayszu;

import java.util.List;

/**
 * As described in <a href="http://www.thefreedictionary.com/Site" alt=
 * "Dictionary definition of website" target="_blank">The Free Dictionary</a>, an website is a set
 * of interconnected web pages, generally located on the same server.
 *
 * <p>
 * In <span class="ayszu-logo">Ayszu</span>, a website, or just <i>site</i>, also is a set of
 * interconnected webpages, or just <i>pages</i>. To each site, it is possible to apply specific
 * {@link Theme}, {@link Plugin}, users, rules, and any other feature available in
 * <span class="ayszu-logo">Ayszu</span> without interfering in other sites.
 *
 * @author Welyab Paula
 *
 * @see Theme
 * @see Plugin
 */
public interface Site {


	/**
	 * The identifier of a site is unique inside <span class="ayszu-logo">Ayszu</span> located in
	 * the same server.
	 *
	 * @return The identifier of site.
	 */
	public String getIdentifier();

	/**
	 * Returns the name of the site.
	 *
	 * @return The name of the site.
	 */
	public String getName();

	/**
	 * Returns the description the site.
	 *
	 * @return The description the site.
	 */
	public String getDescription();

	/**
	 * The path of site is the portion of URL showed in the browser that identifies the site.
	 * Generally, is located after servlet context path until the next slash character (
	 * <code>'/'</code>).
	 *
	 * <p>
	 * A <code>Site</code> path is unique in <code>Ayszu System</code>.
	 *
	 * @return The <code>Site</code> path.
	 *
	 * @see #getPaths()
	 */
	public String getPath();

	/**
	 * @return The registered paths for a <code>Site</code>.
	 *
	 * @see #getPath()
	 */
	public List<String> getPaths();

	/**
	 * Returns the parents of the <code>Site</code>.
	 *
	 * @return The parents of the <code>Site</code>. May be returns a empty list, indicating this
	 *         site has no parents.
	 */
	public List<Site> getParents();
}
