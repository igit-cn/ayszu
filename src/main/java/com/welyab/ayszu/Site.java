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
 * A <code>Site</code> in <code code="Ayszu">Ayszu</code> is a separated area managed by
 * <i>Administration Site</i>.
 *
 * <p>
 * It is possible to apply specific rules, pages, posts, plugins, themes, users, etc. without
 * interfering in another <code>Sites</code>. The permission system of
 * <code class="logo">Ayszu</code>
 *
 * <p>
 * <code>Ayszu</code> has an special <code>Site</code> called <i>Administration Site</i>, that
 * manages other sites.
 *
 * @author Welyab Paula
 *
 * @see Theme
 * @see Plugin
 */
public interface Site {


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
	 * Returns the default <code>Site</code> path.
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
	 * Returns all registered paths of a <code>Site</code>, including the path returned by
	 * <code>{@link #getPath()}</code>.
	 *
	 * A <code>Site</code> path is unique in <code>Ayszu System</code>.
	 *
	 * @return The registered paths for a <code>Site</code>.
	 *
	 * @see #getPath()
	 */
	public List<String> getPaths();

	/**
	 * Returns the parent <code>Site</code>.
	 *
	 * <p>
	 * The unique <code>Site</code> without a parent is the <i>Administration Site</i>, all others
	 * are children of <i>Administration Site</i> or another site. Cyclic <code>Site</code> parent
	 * relation are not allowed.
	 *
	 * @return The parent <code>Site</code>, or <code>null</code> if this site is the
	 *         <i>Administration Site</i>.
	 */
	public Site getParent();
}
