import React, { useState } from 'react';
import './JiraIntegration.css';

function JiraIntegration() {
    const [projectKey, setProjectKey] = useState('');
    const [summary, setSummary] = useState('');
    const [description, setDescription] = useState('');
    const [issueType, setIssueType] = useState('Bug'); // Default to Bug
    const [message, setMessage] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        setMessage('Creating Jira issue...');

        const requestBody = {
            fields: {
                project: {
                    key: projectKey
                },
                summary: summary,
                description: description,
                issuetype: {
                    name: issueType
                }
            }
        };

        try {
            const response = await fetch('/api/jira/create-issue', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestBody),
            });

            if (response.ok) {
                const data = await response.json();
                setMessage(`Jira issue created: ${data.key} (${data.self})`);
                // Clear form
                setProjectKey('');
                setSummary('');
                setDescription('');
            } else {
                const errorData = await response.json();
                setMessage(`Error creating Jira issue: ${errorData.message || response.statusText}`);
            }
        } catch (error) {
            setMessage(`Network error: ${error.message}`);
        }
    };

    return (
        <div className="jira-integration-container">
            <h2>Create Jira Issue</h2>
            <form onSubmit={handleSubmit} className="jira-form">
                <div className="form-group">
                    <label htmlFor="projectKey">Project Key:</label>
                    <input
                        id="projectKey"
                        type="text"
                        value={projectKey}
                        onChange={(e) => setProjectKey(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="summary">Summary:</label>
                    <input
                        id="summary"
                        type="text"
                        value={summary}
                        onChange={(e) => setSummary(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="description">Description:</label>
                    <textarea
                        id="description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        rows="5"
                    ></textarea>
                </div>

                <div className="form-group">
                    <label htmlFor="issueType">Issue Type:</label>
                    <select
                        id="issueType"
                        value={issueType}
                        onChange={(e) => setIssueType(e.target.value)}
                    >
                        <option value="Bug">Bug</option>
                        <option value="Task">Task</option>
                        <option value="Story">Story</option>
                        <option value="Epic">Epic</option>
                    </select>
                </div>

                <button type="submit">Create Issue</button>
            </form>
            {message && <p className="jira-message">{message}</p>}
        </div>
    );
}

export default JiraIntegration;
