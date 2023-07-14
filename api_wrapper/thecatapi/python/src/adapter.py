import json
import typing
import logging

import requests as rq

import exceptions


class Adapter:

    def __init__(self,
                 scheme: str,
                 hostname: str,
                 version: str,
                 api_key: typing.Optional[str] = "",
                 ssl_verify: typing.Optional[bool] = True,
                 logger: typing.Optional[logging.Logger] = None) -> None:
        self._hostname = hostname
        self._api_key = api_key
        self._version = version
        self._ssl_verify = ssl_verify
        self._logger = logger or logging.getLogger("thecatapi")
        self._BASE_URL = f"{scheme}://{hostname}/{version}/"
        self._session = rq.Session()
        self._session.verify = self._ssl_verify
        self._session.headers.update({
            "x-api-key": api_key,
            # "Content-Type": "application/json"
        })

        if not ssl_verify:
            self._logger.warning("SSL verification disabled! Enabling it is strongly advised!")
            rq.packages.urllib3.disable_warnings()

    @staticmethod
    def to_json(response: rq.Response) -> typing.Union[dict, list]:
        try:
            return response.json()
        except (ValueError, json.JSONDecodeError):
            raise exceptions.TheCatAPIException(f"Bad json!\n{response.text}")

    def request(self,
                method: typing.Literal["GET", "POST", "DELETE"],
                endpoint: str,
                check_status: typing.Optional[bool] = True,
                data: typing.Optional[dict] = None,
                params: typing.Optional[dict] = None) -> rq.Response:
        url = self._BASE_URL + endpoint
        self._logger.debug(f"Making request, method: {method}, url: {url}")

        try:
            response = self._session.request(method, url, params=params, data=data)
        except rq.exceptions.RequestException as e:
            self._logger.exception(e)
            raise exceptions.TheCatAPIException(f"Request failed, url: {url}")

        status_code = response.status_code
        if check_status:
            if (status_code < 200) or (status_code >= 300):
                self._logger.warning(f"Status code: {status_code}, reason: {response.reason}")
                raise exceptions.TheCatAPIException(response.text)

        self._logger.debug(f"SUCCESS, status code: {status_code}, reason: {response.reason}")
        return response

    def get(self, endpoint: str, **params: typing.Any) -> rq.Response:
        return self.request("GET", endpoint, params=params)

    def post(self,
             endpoint: str,
             data: typing.Optional[dict] = None,
             params: typing.Optional[dict] = None) -> rq.Response:
        return self.request("POST", endpoint, data=data, params=params)

    def delete(self,
               endpoint: str,
               data: typing.Optional[dict] = None,
               params: typing.Optional[dict] = None) -> rq.Response:
        return self.request("DELETE", endpoint, data=data, params=params)
