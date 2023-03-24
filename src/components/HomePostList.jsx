import React from 'react';
import HomePost from './HomePost';
import './HomePost.css';
import { NavLink, Routes, Route } from 'react-router-dom';

export default function HomePostList({posts}) {
	return ( <ul className="PostLinks">{
		posts.map((post) => {
			return <li> 
				<NavLink to= {'/post?pid=' + post._id}>
					<HomePost key={post._id} post={post}/>
				</NavLink> 
			</li>
		})
		}</ul>
	)
}
