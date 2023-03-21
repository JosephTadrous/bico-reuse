import React from 'react';
import HomePost from './HomePost';

export default function HomePostList({posts}) {
	return (
		posts.map((post) => {
			return <HomePost key={post._id} post={post} />
		})
	)
}
