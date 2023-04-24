import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';


export default function EditProfileForm() {
    const location= useLocation();
	const { state }= location;
	const user = state.user;


    const navigate = useNavigate();
    const clickHandler = () => {
        navigate("/");
    }

    const requestUrl = `http://localhost:3000/update?id=${user._id}`;

	return (
		<div className="EditForm">
			<h1>Edit Profile</h1>
			<form className="InputForm" action={requestUrl} method="post">
				<div className="FormName"><div className="TextContainer">Name:</div> <input name="name" defaultValue={user.name}/> </div>
				<div className="FormCollege"><div className="TextContainer">College:</div> <input name="college" defaultValue={user.college}/></div>
				<div className="FormEmail"><div className="TextContainer">Email:</div> <input name="email" defaultValue={user.email}/></div>
				<div className="FormPhone"><div className="TextContainer">Phone:</div> <input name="phone" defaultValue={user.phone}/></div>
				
				<div className="Buttons">
					<button onClick={clickHandler} className="CancelButton" type="submit">Cancel</button>
					<button className="EditButton" type="submit">Submit</button>
				</div>
			</form>
		</div>
	)
}