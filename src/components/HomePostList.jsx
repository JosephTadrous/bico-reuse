import React from 'react';
import HomePost from './HomePost';
import './Home.css'; 
import { NavLink } from 'react-router-dom';

export default function HomePostList({posts}) {
	return ( <ul className="PostLinks">{
		posts.map((post) => {
			return <li key={post._id}> 
				<NavLink to= {'/post?pid=' + post._id} >
					<HomePost post={post}/>
				</NavLink> 
			</li>
		})
		}</ul>
	)
}
