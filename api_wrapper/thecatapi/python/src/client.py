import typing
import logging

import adapter
import exceptions
from models import breed as _breed
from models import image as _image
from models import category as _category


class Client:

    def __init__(self,
                 api_key: typing.Optional[str] = "",
                 ssl_verify: typing.Optional[bool] = True,
                 logger: typing.Optional[logging.Logger] = None) -> None:
        self._adapter = adapter.Adapter("https", "api.thecatapi.com", "v1", api_key, ssl_verify, logger)

    def breed(self, id: str) -> _breed.Breed:
        response = self._adapter.get(f"breeds/{id}")
        body = self._adapter.to_json(response)
        return _breed.Breed(**body)

    def breeds(self,
               limit: typing.Optional[int] = 10,
               page: typing.Optional[int] = 0) -> list[_breed.Breed]:
        params = {
            "limit": limit,
            "page": page
        }
        response = self._adapter.get("breeds", **params)
        body = self._adapter.to_json(response)
        output = [
            _breed.Breed(**d)
            for d in body
        ]

        return output

    def category(self) -> list[_category.Category]:
        response = self._adapter.get("categories")
        body = self._adapter.to_json(response)
        output = [
            _category.Category(**d)
            for d in body
        ]

        return output

    def search_image(self,
                     ) -> list[_image.Image]:
        pass
