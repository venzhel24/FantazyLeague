import React, {useState} from 'react';
import DefaultButton from "../../components/UI/button/DefaultButton";
import AdminService from "../../services/AdminService";
import {useFetching} from "../../hooks/useFetching";

const UploadRace = () => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [selectedFileName, setSelectedFileName] = useState('');
    const [uploadResponse, setUploadResponse] = useState(null);

    const [resultsUpload, isResultsUploading, resultsUploadingError] = useFetching(async (formData) => {
        const uploadingResponse = await AdminService.uploadRace(formData);
        setUploadResponse(uploadingResponse);
    });

    function handleFileChange(event) {
        setSelectedFile(event.target.files[0]);
        setSelectedFileName(event.target.files[0].name);
    }

    const handleUpload = () => {
        if (selectedFile) {
            console.log('selectedFiles');
            const formData = new FormData();
            formData.append('file', selectedFile);

            resultsUpload(formData);
        }
    }


    return (
        <div className={"column"}>
            <div className={"admin_block"}>
                <h2>Upload pdf results file</h2>
                <div className="file_upload">
                    <input type="file" id="pdfUpload" className="file_input" onChange={handleFileChange}/>
                    <label htmlFor="pdfUpload" className="file_label">Choose File</label>
                    <p>{selectedFileName}</p>
                </div>
                <button className={"upload_button"} onClick={handleUpload}>Upload</button>
                <p className={"upload_response"}>{uploadResponse}</p>
            </div>
        </div>
    );
};

export default UploadRace;