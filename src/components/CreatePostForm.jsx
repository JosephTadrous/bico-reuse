import React from 'react';

export default function CreatePostForm({post}) {
	return (
		<html> 
        <form action="http://localhost:3000/create_post" method="post">
        User ID: <input name="user_id"/> <p> </p>
        Title: <input name="title"/>  <p> </p>
        Description: <input name="description"/> <p> </p>
        Price: <input name="price"/> <p> </p>
        Image: <input type="image" name="image"/> <p> </p>
        <input type="submit" value="Submit form!"/>
        </form>
        </html>
	)
}
