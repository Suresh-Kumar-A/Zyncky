
# Zyncky

Zyncky is an online file storage application that allows users to securely upload, download & share files. It is a replica of popular storage providers like GoogleDrive, OneDrive, etc.,


## Features

- User Management
- File Storage (upto 50MB)(.txt, .pdf, .jpg, .png, .mp4)
- Search and File Management
- File Preview
- Cloud Storage Insights (Display used vs. available storage)(Breakdown of file usage by type)
- Security & Access Management

## Roadmap

- File Sharing
- Real-time collaboration
- Add more integrations
- Custom File Tags/Labels
- File Compression and Extraction (.tar, .rar, .zip)
- Trash/Recycle Bin
- File Versioning
- Custom Notifications


## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`API_KEY`

`ANOTHER_API_KEY`


## Run Locally

Clone the project

```bash
  git clone https://link-to-project
```

Go to the project directory

```bash
  cd my-project
```

Install dependencies

```bash
  npm install
```

Start the server

```bash
  npm run start
```


## API Reference

#### Get all items

```http
  GET /api/items
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get item

```http
  GET /api/items/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### add(num1, num2)

Takes two numbers and returns the sum.


## Authors

- [@suresh-kumar-a](https://github.com/Suresh-Kumar-A/)

