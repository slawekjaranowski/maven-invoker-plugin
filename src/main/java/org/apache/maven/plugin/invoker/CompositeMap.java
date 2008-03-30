/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.plugin.invoker;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.introspection.ReflectionValueExtractor;

/**
 * @author <a href="mailto:olamy@apache.org">olamy</a>
 * @since 22 nov. 07
 * @version $Id$
 */
class CompositeMap
    implements Map
{

    private MavenProject mavenProject;

    private Properties properties;

    protected CompositeMap(MavenProject mavenProject, Properties properties)
    {
        this.mavenProject = mavenProject;
        this.properties = properties == null ? new Properties() : properties;
    }

    /**
     * @see java.util.Map#clear()
     */
    public void clear()
    {
        // nothing here

    }

    /**
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    public boolean containsKey( Object key )
    {
        if ( key == null )
        {
            return false;
        }
        try
        {
            Object evaluated = ReflectionValueExtractor.evaluate( (String) key, this.mavenProject );
            return evaluated == null;
        }
        catch ( Exception e )
        {
            // uhm do we have to throw a RuntimeException here ?
        }

        return ( mavenProject.getProperties().containsKey( key ) || properties.containsKey( key ) );
    }

    /**
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    public boolean containsValue( Object value )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Map#entrySet()
     */
    public Set entrySet()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Map#get(java.lang.Object)
     */
    public Object get( Object key )
    {
        if ( key == null )
        {
            return null;
        }
        try
        {
            Object evaluated = ReflectionValueExtractor.evaluate( (String) key, this.mavenProject );
            if ( evaluated != null )
            {
                return evaluated;
            }
        }
        catch ( Exception e )
        {
            // uhm do we have to throw a RuntimeException here ?
        }

        Object value = properties.get( key );

        return ( value != null ? value : this.mavenProject.getProperties().get( key ) );

    }

    /**
     * @see java.util.Map#isEmpty()
     */
    public boolean isEmpty()
    {
        return this.mavenProject == null && this.mavenProject.getProperties().isEmpty() && this.properties.isEmpty();
    }

    /**
     * @see java.util.Map#keySet()
     */
    public Set keySet()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    public Object put( Object key, Object value )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Map#putAll(java.util.Map)
     */
    public void putAll( Map t )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Map#remove(java.lang.Object)
     */
    public Object remove( Object key )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Map#size()
     */
    public int size()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Map#values()
     */
    public Collection values()
    {
        throw new UnsupportedOperationException();
    }
}